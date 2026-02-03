# SQL Interview Questions for Backend Engineering

**Target role:** Back End Java Developer / Senior Backend Engineer (Fidelity JD)  
**Focus:** PostgreSQL, SQL/Oracle, relational DBs, data design, ETL/data pipelines

---

## 1. Window Functions — Core Concepts

**Q1.1** What is a window function? How does it differ from a regular aggregate with `GROUP BY`? When would you use `PARTITION BY` vs `GROUP BY`?

**A:** A **window function** computes a value for each row based on a set of related rows (the "window"), without collapsing rows. A regular aggregate with `GROUP BY` collapses rows into one per group. Use **PARTITION BY** to define logical groups within the window (e.g., per department); rows in the same partition share the same window context. Use **GROUP BY** when you want aggregated output (one row per group).

---

**Q1.2** Explain the difference between `ROW_NUMBER()`, `RANK()`, and `DENSE_RANK()`. Give a scenario (e.g., top 3 salaries per department) where each would produce different results.

**A:** For 90k, 85k, 85k, 70k: ROW_NUMBER=1,2,3,4; RANK=1,2,2,4; DENSE_RANK=1,2,2,3. Top 3 per dept: use DENSE_RANK for ties.

---

**Q1.3** What is a window frame? Explain `ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW` vs `RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW`. When does the choice of frame matter for running totals or moving averages?

**A:** A **window frame** defines which rows in the partition are used. **ROWS** = physical rows; **RANGE** = rows with same ORDER BY value (peers). For running totals with ties in sort key: ROWS gives different values per physical row; RANGE gives same value for tied rows. For date-based moving avg, use `RANGE BETWEEN INTERVAL '7' DAY PRECEDING AND CURRENT ROW`.

---

## 2. Window Functions — Ordering & Partitioning

**Q2.1** Write a query that returns each employee's salary along with the **previous** and **next** salary within the same department using `LAG()` and `LEAD()`. How do you handle the first/last row (default value)?

**A:** Use 3rd arg to LAG/LEAD for default: `LAG(salary, 1, NULL)`, `LEAD(salary, 1, NULL)`. NULL for first/last row.
```sql
SELECT name, dept_id, salary,
  LAG(salary) OVER (PARTITION BY dept_id ORDER BY salary, id) AS prev_salary,
  LEAD(salary) OVER (PARTITION BY dept_id ORDER BY salary, id) AS next_salary
FROM employees;
```

---

**Q2.2** For a table of orders (order_id, customer_id, order_date, amount), write SQL to compute a **running total** of amount per customer ordered by order_date. Use a window function with an explicit frame.

**A:**
```sql
SELECT customer_id, order_date, amount,
  SUM(amount) OVER (
    PARTITION BY customer_id
    ORDER BY order_date, order_id
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
  ) AS running_total
FROM orders
ORDER BY customer_id, order_date;
```

---

**Q2.3** Nth highest problem: given an `employees(id, name, salary)` table, write a query that returns the **2nd highest salary**. Do it using (a) `DENSE_RANK()` in a subquery/CTE, and (b) `LIMIT`/`OFFSET` (and mention pitfalls of ties and empty results).

**A:** (a) `WITH ranked AS (SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) rk FROM employees) SELECT salary FROM ranked WHERE rk=2`. (b) `SELECT DISTINCT salary FROM employees ORDER BY salary DESC LIMIT 1 OFFSET 1`. **Pitfalls:** ties—OFFSET returns 1 row, DENSE_RANK returns all; empty—fewer than 2 rows gives no result.

---

## 3. Window Functions — Advanced

**Q3.1** Explain `FIRST_VALUE()` and `LAST_VALUE()` with and without a proper window frame. Why is `LAST_VALUE()` often used with `ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING`?

**A:** Default frame is `ROWS UNBOUNDED PRECEDING AND CURRENT ROW`, so LAST_VALUE returns current row, not last in partition. Use `ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING` to get the actual last value in the partition.

---

**Q3.2** What does `NTILE(n)` do? Give an example: splitting a result set into 4 quartiles by some metric.

**A:** NTILE(n) splits rows into n buckets (1 to n). `NTILE(4) OVER (ORDER BY salary)` assigns quartiles; 1=lowest 25%, 4=highest 25%.

---

**Q3.3** Can you use an aggregate function (e.g., `SUM()`, `AVG()`) as a window function? Write a query that shows **department total salary** and **each employee's salary as a percentage of that department total** using window `SUM()`.

**A:** Yes. Aggregates can be used as window functions.
```sql
SELECT name, dept_id, salary,
  SUM(salary) OVER (PARTITION BY dept_id) AS dept_total,
  ROUND(100.0 * salary / SUM(salary) OVER (PARTITION BY dept_id), 2) AS pct_of_dept
FROM employees;
```

---

## 4. Joins & Set Operations

**Q4.1** Describe `INNER JOIN`, `LEFT OUTER JOIN`, `RIGHT OUTER JOIN`, and `FULL OUTER JOIN` with a simple two-table example. When would you use `CROSS JOIN`?

**A:** **INNER** = only matching rows. **LEFT** = all from left + matches from right (NULL if no match). **RIGHT** = all from right + matches from left. **FULL** = all from both, NULL where no match. **CROSS** = Cartesian product (every row of A with every row of B); use for generating combinations (e.g., all date-product pairs).

---

**Q4.2** Given `employees(dept_id, ...)` and `departments(id, name)`, write a query that returns **department name and count of employees**, including departments with zero employees.

**A:**
```sql
SELECT d.name, COUNT(e.id) AS employee_count
FROM departments d
LEFT JOIN employees e ON d.id = e.dept_id
GROUP BY d.id, d.name;
```
Use `COUNT(e.id)` not `COUNT(*)` so zeros stay 0.

---

**Q4.3** What is the difference between `IN`, `EXISTS`, and `JOIN` when used to express "rows in A that have a match in B"? When is `EXISTS` preferable for performance?

**A:** **IN** = subquery returns list, check membership. **EXISTS** = returns true/false if subquery returns any row; can short-circuit. **JOIN** = combines tables. EXISTS is preferable when: subquery can return early on first match; avoids duplicate handling; often better with semi-join optimization.

---

## 5. Subqueries & CTEs

**Q5.1** What is a CTE? How does it differ from a subquery in terms of readability and reusability? Can a CTE be referenced multiple times in the same query?

**A:** CTE = Common Table Expression (`WITH name AS (SELECT...)`). More readable than nested subqueries. Can be referenced multiple times (avoids repeating logic). Yes, a CTE can be referenced multiple times in the same query.

---

**Q5.2** Write a query using a **correlated subquery**: for each employee, return their name and whether their salary is above the average salary of their department.

**A:**
```sql
SELECT name, salary,
  CASE WHEN salary > (SELECT AVG(salary) FROM employees e2 WHERE e2.dept_id = e.dept_id)
       THEN 'Yes' ELSE 'No' END AS above_dept_avg
FROM employees e;
```

---

**Q5.3** When would you use a **recursive CTE**? Give a classic example (e.g., employee hierarchy or graph traversal).

**A:** Use for hierarchical/graph data: org charts, bill of materials, tree traversal. Example: employee hierarchy with `manager_id`—anchor selects top-level employees; recursive member joins to find reports.

---

## 6. Aggregation & GROUP BY

**Q6.1** Explain `GROUP BY` and `HAVING`. Why can't you use `WHERE` to filter on aggregate results (e.g., departments with more than 5 employees)?

**A:** **GROUP BY** defines groups; **HAVING** filters groups *after* aggregation. WHERE runs before aggregation, so aggregates don't exist yet. Use HAVING for `COUNT(*) > 5`.

**SQL evaluation order:** `FROM → WHERE → GROUP BY → aggregates → HAVING → SELECT → ORDER BY`

---

**Q6.2** What is `GROUPING SETS`, `ROLLUP`, and `CUBE`? Give a use case for `ROLLUP` (e.g., subtotals by region and overall total).

**A:** **GROUPING SETS** = explicit list of grouping combinations. **ROLLUP** = hierarchical subtotals (e.g., by region, then grand total). **CUBE** = all combinations. ROLLUP use case: `GROUP BY ROLLUP(region)` gives per-region totals + grand total.

---

## 7. Indexes & Performance

**Q7.1** How do B-tree indexes help `WHERE`, `ORDER BY`, and `JOIN`? When might an index **not** be used (e.g., functions on columns, type mismatch)?

**A:** B-tree supports range scans and sorted order. Helps: WHERE on indexed col; ORDER BY on indexed col; JOIN on indexed FK. Not used: `WHERE UPPER(name)='X'` (function on column); type mismatch (e.g., string vs number); low selectivity; optimizer chooses full scan for small tables.

---

**Q7.2** How would you troubleshoot a slow query? What would you look at (execution plan, indexes, statistics, locking)?

**A:** Run EXPLAIN (ANALYZE); check for sequential scans on large tables; missing indexes on WHERE/JOIN/ORDER BY; outdated statistics (run ANALYZE); locking/blocking (pg_locks, pg_stat_activity); inefficient plans (nested loops on large sets).

---

**Q7.3** What is a **covering index** (index-only scan)? When is it useful?

**A:** Index that contains all columns needed by the query—avoids heap lookup. Useful when SELECT columns are few and all in index; reduces I/O. Create with `CREATE INDEX idx ON t(a, b) INCLUDE (c)` (PostgreSQL) so index covers a, b, c.

---

## 8. Transactions & Integrity

**Q8.1** Explain ACID. What do `COMMIT` and `ROLLBACK` do? What is a **savepoint** and when would you use it?

**A:** **ACID**: Atomicity (all or nothing), Consistency (valid state), Isolation (concurrent transactions don't see uncommitted data), Durability (committed survives crash). COMMIT makes changes permanent; ROLLBACK undoes. Savepoint = named point within transaction; ROLLBACK TO savepoint undoes to that point (useful for partial rollback).

---

**Q8.2** What are isolation levels (e.g., Read Committed, Repeatable Read, Serializable)? What is **dirty read**, **non-repeatable read**, and **phantom read**?

**A:** **Read Committed**: see only committed data. **Repeatable Read**: same query in tx sees same snapshot. **Serializable**: no anomalies. **Dirty read** = see uncommitted data. **Non-repeatable read** = same row different in same tx. **Phantom read** = new rows appear in same query.

---

## 9. Data Design & Normalization

**Q9.1** What is normalization? Briefly explain 1NF, 2NF, 3NF and when denormalization might be acceptable for performance.

**A:** Normalization reduces redundancy. **1NF**: atomic values, no repeating groups. **2NF**: 1NF + no partial dependency (non-key depends on full key). **3NF**: 2NF + no transitive dependency (non-key depends only on key). Denormalize for read-heavy workloads, reporting, when joins are costly.

---

**Q9.2** When would you use a **composite primary key** vs a surrogate key (e.g., auto-increment id)?

**A:** **Composite PK**: natural key exists (e.g., order_id + line_item_id); junction tables (many-to-many). **Surrogate**: no natural key; simplifies FKs; stable when business key changes; preferred for most OLTP.

---

## 10. PostgreSQL / JSON & Practical Scenarios

**Q10.1** How do you store and retrieve a JSON/JSONB value in PostgreSQL? When would you use `JSONB` instead of `JSON`?

**A:** `CREATE TABLE t (data JSONB); INSERT INTO t VALUES ('{"key":"value"}'); SELECT data->>'key'` or `data->'key'`. **JSONB** = binary, indexed, no duplicate keys, faster for queries; use for storage and querying. **JSON** = text, preserves formatting; use when exact replica matters.

---

**Q10.2** Design question: Two tables — `employees(id, name, dept_id, salary)` and `departments(id, name)`. Write SQL for: (a) department name and count of employees; (b) employee id and max salary (per department).

**A:** (a) `SELECT d.name, COUNT(e.id) FROM departments d LEFT JOIN employees e ON d.id=e.dept_id GROUP BY d.id, d.name`. (b) Per dept: `SELECT dept_id, id, salary FROM (SELECT *, MAX(salary) OVER (PARTITION BY dept_id) m FROM employees) x WHERE salary=m` or use DENSE_RANK.

---

**Q10.3** Scenario: If one user mistakenly enters their name in one column vs two columns — how do you retract and save properly?

**A:** Use transaction: BEGIN; UPDATE users SET first_name='X', last_name='Y' WHERE id=Z; verify; COMMIT (or ROLLBACK). Consider: backup/audit table; soft delete + insert for audit trail; validation before commit.

---

## Quick Reference

### SQL Evaluation Order

`FROM → WHERE → GROUP BY → aggregates → HAVING → SELECT → ORDER BY`

---

### Window Function Syntax

```sql
function_name(...) OVER (
  [PARTITION BY expr [, ...]]
  [ORDER BY expr [ASC|DESC] [, ...]]
  [frame_clause]
)

-- Frame examples:
ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
RANGE BETWEEN INTERVAL '7' DAY PRECEDING AND CURRENT ROW
```

---

## Summary Checklist

| Topic                     | Covered |
|---------------------------|--------|
| Window: ROW_NUMBER/RANK/DENSE_RANK | ✓ |
| Window: LAG/LEAD          | ✓ |
| Window: FIRST_VALUE/LAST_VALUE, NTILE | ✓ |
| Window: frames (ROWS vs RANGE) | ✓ |
| Window: aggregates (SUM, AVG) over window | ✓ |
| Joins (INNER, LEFT, FULL, CROSS) | ✓ |
| EXISTS vs IN vs JOIN      | ✓ |
| CTEs, correlated subqueries, recursive CTE | ✓ |
| GROUP BY, HAVING, ROLLUP/CUBE | ✓ |
| Indexes, execution plans, covering index | ✓ |
| Transactions, isolation levels | ✓ |
| Normalization, keys       | ✓ |
| PostgreSQL JSON/JSONB     | ✓ |
| Practical scenario (name in one vs two columns) | ✓ |

Use these questions to practice writing SQL by hand and explaining trade-offs—especially for window functions, since they are commonly asked for backend and data-platform roles.
