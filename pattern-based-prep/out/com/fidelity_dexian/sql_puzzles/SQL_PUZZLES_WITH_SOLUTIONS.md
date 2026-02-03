# SQL Puzzles — Tables, Problems & Solutions

All puzzles use the same schema. Data is shown as tables below (no INSERT scripts).

---

## Quick Reference — Templates to Remember

### WHERE vs HAVING
- **WHERE** — filters **rows** (before grouping); no aggregates
- **HAVING** — filters **groups** (after aggregation); use with COUNT/SUM/AVG

**SQL evaluation order:** `FROM → WHERE → GROUP BY → aggregates → HAVING → SELECT → ORDER BY`

```sql
SELECT col, COUNT(*) FROM t
WHERE col > 100           -- filter rows first
GROUP BY col
HAVING COUNT(*) > 2;      -- filter groups after
```

### CTE (Common Table Expression)
- **CTE** = Common Table Expression — temporary named result set, not "Create Table"
- Use for readability and when you need to reference the result multiple times

```sql
WITH name AS (
  SELECT col1, col2 FROM table
)
SELECT * FROM name WHERE ...;
```

### DENSE_RANK — Nth / Top N
- `DENSE_RANK()` — ties get same rank, no gaps (1,2,2,3 vs RANK: 1,2,2,4)
- Nth overall: omit `PARTITION BY`
- Top N per group: add `PARTITION BY group_col`

```sql
WITH ranked AS (
  SELECT *, DENSE_RANK() OVER (PARTITION BY dept_id ORDER BY salary DESC) AS rk
  FROM employees
)
SELECT * FROM ranked WHERE rk <= 2;   -- top 2 per dept
-- OR  WHERE rk = 2;                  -- 2nd highest
```

### LAG / LEAD — Previous / Next Row
- `LAG(col)` — value from previous row in partition
- `LEAD(col)` — value from next row

```sql
SELECT col,
  LAG(col)  OVER (PARTITION BY group_col ORDER BY sort_col) AS prev_val,
  LEAD(col) OVER (PARTITION BY group_col ORDER BY sort_col) AS next_val
FROM table;
```

### Running Total (Window Frame)
```sql
SUM(amount) OVER (
  PARTITION BY customer_id
  ORDER BY order_date
  ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
) AS running_total
```

### Include Zeros (LEFT JOIN + COUNT)
- Use **LEFT JOIN** from the "master" table so rows with no matches still appear
- `COUNT(e.id)` not `COUNT(*)` — counts only non-null FK matches (zeros stay 0)

```sql
SELECT d.name, COUNT(e.id)
FROM departments d
LEFT JOIN employees e ON d.id = e.dept_id
GROUP BY d.id, d.name;
```

---

## Tables & Schema

### `departments`

| Column | Type |
|--------|------|
| id | INT (PK) |
| name | VARCHAR |

**Data:**

| id | name |
|----|------|
| 10 | Engineering |
| 20 | Sales |
| 30 | HR |

---

### `employees`

| Column | Type |
|--------|------|
| id | INT (PK) |
| name | VARCHAR |
| dept_id | INT (FK → departments.id) |
| salary | DECIMAL(10,2) |
| hire_date | DATE |

**Data:**

| id | name   | dept_id | salary  | hire_date   |
|----|--------|---------|---------|-------------|
| 1  | Alice  | 10      | 90000   | 2020-01-15  |
| 2  | Bob    | 10      | 85000   | 2019-06-01  |
| 3  | Carol  | 10      | 85000   | 2021-03-10  |
| 4  | Dave   | 10      | 70000   | 2022-01-01  |
| 5  | Eve    | 20      | 80000   | 2018-11-20  |
| 6  | Frank  | 20      | 75000   | 2020-05-12  |
| 7  | Grace  | 20      | 72000   | 2021-09-01  |
| 8  | Henry  | 30      | 65000   | 2020-02-28  |

---

### `orders`

| Column | Type |
|--------|------|
| order_id | INT (PK) |
| customer_id | INT |
| order_date | DATE |
| amount | DECIMAL(10,2) |

**Data:**

| order_id | customer_id | order_date | amount |
|----------|-------------|------------|--------|
| 101 | 1 | 2024-01-05 | 100 |
| 102 | 1 | 2024-01-12 | 150 |
| 103 | 1 | 2024-01-20 | 80 |
| 104 | 2 | 2024-01-08 | 200 |
| 105 | 2 | 2024-01-15 | 120 |
| 106 | 3 | 2024-01-10 | 90 |

---

### `customers`

| Column | Type |
|--------|------|
| id | INT (PK) |
| name | VARCHAR |
| region | VARCHAR |

**Data:**

| id | name   | region |
|----|--------|--------|
| 1  | Acme   | North  |
| 2  | Beta   | North  |
| 3  | Gamma  | South  |
| 4  | Delta  | South  |

---

### `sales`

| Column | Type |
|--------|------|
| id | INT (PK) |
| region | VARCHAR |
| product | VARCHAR |
| amount | DECIMAL(10,2) |

**Data:**

| id | region | product | amount |
|----|--------|---------|--------|
| 1  | North  | A       | 1000   |
| 2  | North  | B       | 800    |
| 3  | South  | A       | 600    |
| 4  | South  | B       | 900    |

---

## Puzzle 1 — Department name and employee count (including zero)

**Problem:** Return department name and number of employees per department. Include departments with zero employees.

**Solution:**

```sql
SELECT d.name AS department_name, COUNT(e.id) AS employee_count
FROM departments d
LEFT JOIN employees e ON d.id = e.dept_id
GROUP BY d.id, d.name
ORDER BY d.id;
```

**Result:**

| department_name | employee_count |
|-----------------|----------------|
| Engineering     | 4              |
| Sales           | 3              |
| HR              | 1              |

---

## Puzzle 2 — Nth highest salary (2nd highest)

**Problem:** Return the 2nd highest salary in the company. Use `DENSE_RANK()` so ties don’t skip ranks.

**Solution:**

```sql
WITH ranked AS (
  SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rk
  FROM employees
)
SELECT salary AS second_highest_salary
FROM ranked
WHERE rk = 2;
```

**Result:**

| second_highest_salary |
|-----------------------|
| 85000                 |

(85000 appears twice; 2nd rank is still one row in the CTE; `WHERE rk = 2` returns 85000.)

---

## Puzzle 3 — Previous and next salary per department (LAG / LEAD)

**Problem:** For each employee, show name, department, salary, and the previous and next salary within the same department (by salary order). Use NULL where there is no previous/next row.

**Solution:**

```sql
SELECT
  e.name,
  d.name AS department,
  e.salary,
  LAG(e.salary) OVER (PARTITION BY e.dept_id ORDER BY e.salary, e.id) AS prev_salary,
  LEAD(e.salary) OVER (PARTITION BY e.dept_id ORDER BY e.salary, e.id) AS next_salary
FROM employees e
JOIN departments d ON e.dept_id = d.id
ORDER BY e.dept_id, e.salary, e.id;
```

**Result (conceptually):**

| name  | department  | salary | prev_salary | next_salary |
|-------|-------------|--------|-------------|-------------|
| Dave  | Engineering | 70000  | NULL        | 85000       |
| Bob   | Engineering | 85000  | 70000       | 85000       |
| Carol | Engineering | 85000  | 85000       | 90000       |
| Alice | Engineering | 90000  | 85000       | NULL        |
| ...   | ...         | ...    | ...         | ...         |

---

## Puzzle 4 — Running total of order amount per customer

**Problem:** For each order, show customer_id, order_date, amount, and running total of amount for that customer ordered by order_date.

**Solution:**

```sql
SELECT
  customer_id,
  order_date,
  amount,
  SUM(amount) OVER (
    PARTITION BY customer_id
    ORDER BY order_date, order_id
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
  ) AS running_total
FROM orders
ORDER BY customer_id, order_date;
```

**Result:**

| customer_id | order_date | amount | running_total |
|-------------|------------|--------|---------------|
| 1           | 2024-01-05 | 100    | 100           |
| 1           | 2024-01-12 | 150    | 250           |
| 1           | 2024-01-20 | 80     | 330           |
| 2           | 2024-01-08 | 200    | 200           |
| 2           | 2024-01-15 | 120    | 320           |
| 3           | 2024-01-10 | 90     | 90            |

---

## Puzzle 5 — Salary as percentage of department total

**Problem:** For each employee, show name, department, salary, department total salary, and salary as a percentage of that department total.

**Solution:**

```sql
SELECT
  e.name,
  d.name AS department,
  e.salary,
  SUM(e.salary) OVER (PARTITION BY e.dept_id) AS dept_total,
  ROUND(100.0 * e.salary / SUM(e.salary) OVER (PARTITION BY e.dept_id), 2) AS pct_of_dept
FROM employees e
JOIN departments d ON e.dept_id = d.id
ORDER BY e.dept_id, e.salary DESC;
```

**Result (conceptually):**

| name  | department  | salary | dept_total | pct_of_dept |
|-------|-------------|--------|------------|-------------|
| Alice | Engineering | 90000  | 330000     | 27.27       |
| Bob   | Engineering | 85000  | 330000     | 25.76       |
| ...   | ...         | ...    | ...        | ...         |

---

## Puzzle 6 — Top N per group (top 2 salaries per department)

**Problem:** Return employees who have one of the top 2 salaries in their department. Use `DENSE_RANK()` so ties for 2nd still count as “top 2.”

**Solution:**

```sql
WITH ranked AS (
  SELECT e.id, e.name, d.name AS department, e.salary,
         DENSE_RANK() OVER (PARTITION BY e.dept_id ORDER BY e.salary DESC) AS rk
  FROM employees e
  JOIN departments d ON e.dept_id = d.id
)
SELECT id, name, department, salary, rk
FROM ranked
WHERE rk <= 2
ORDER BY department, rk, name;
```

**Result:**

| id | name  | department  | salary | rk |
|----|-------|-------------|--------|----|
| 1  | Alice | Engineering | 90000  | 1  |
| 2  | Bob   | Engineering | 85000  | 2  |
| 3  | Carol | Engineering | 85000  | 2  |
| 5  | Eve   | Sales       | 80000  | 1  |
| 6  | Frank | Sales       | 75000  | 2  |
| 8  | Henry | HR          | 65000  | 1  |

---

## Puzzle 7 — Employees above department average (correlated subquery)

**Problem:** For each employee, show name, department, salary, and whether their salary is above their department’s average salary.

**Solution:**

```sql
SELECT
  e.name,
  d.name AS department,
  e.salary,
  CASE WHEN e.salary > (
    SELECT AVG(e2.salary) FROM employees e2 WHERE e2.dept_id = e.dept_id
  ) THEN 'Yes' ELSE 'No' END AS above_dept_avg
FROM employees e
JOIN departments d ON e.dept_id = d.id
ORDER BY e.dept_id, e.salary DESC;
```

**Result (conceptually):** One row per employee with `above_dept_avg` = 'Yes' or 'No' based on department average.

---

## Puzzle 8 — Subtotals by region and grand total (ROLLUP)

**Problem:** Using `sales`, show amount by region, and also subtotals per region and a grand total.

**Solution:**

```sql
SELECT region, SUM(amount) AS total_amount
FROM sales
GROUP BY ROLLUP(region)
ORDER BY region;
```

**Result:**

| region | total_amount |
|--------|--------------|
| North  | 1800         |
| South  | 1500         |
| NULL   | 3300         |

(NULL row is the grand total.)

---

## Puzzle 9 — Departments with more than 2 employees (GROUP BY + HAVING)

**Problem:** Return department name and employee count only for departments that have more than 2 employees.

**Solution:**

```sql
SELECT d.name AS department_name, COUNT(e.id) AS employee_count
FROM departments d
JOIN employees e ON d.id = e.dept_id
GROUP BY d.id, d.name
HAVING COUNT(e.id) > 2
ORDER BY employee_count DESC;
```

**Result:**

| department_name | employee_count |
|-----------------|----------------|
| Engineering     | 4              |
| Sales           | 3              |

---

## Puzzle 10 — Quartiles by salary (NTILE)

**Problem:** Assign each employee to one of 4 quartiles by salary (1 = lowest 25%, 4 = highest 25%) and show name, salary, and quartile.

**Solution:**

```sql
SELECT
  name,
  salary,
  NTILE(4) OVER (ORDER BY salary) AS quartile
FROM employees
ORDER BY salary, name;
```

**Result (conceptually):**

| name   | salary | quartile |
|--------|--------|----------|
| Henry  | 65000  | 1        |
| Grace  | 72000  | 1        |
| Dave   | 70000  | 2        |
| Frank  | 75000  | 2        |
| Bob    | 85000  | 3        |
| Carol  | 85000  | 3        |
| Eve    | 80000  | 3        |
| Alice  | 90000  | 4        |

(Exact quartile boundaries depend on tie-breaking; NTILE(4) splits the 8 rows into 4 buckets.)

---

## Topic coverage

| Topic                    | Puzzle(s)      |
|--------------------------|----------------|
| LEFT JOIN + COUNT       | 1              |
| DENSE_RANK, CTE         | 2, 6           |
| LAG / LEAD              | 3              |
| Running total, frame     | 4              |
| Window SUM, percentage   | 5              |
| Correlated subquery      | 7              |
| ROLLUP                   | 8              |
| HAVING                   | 9              |
| NTILE                    | 10             |

Use the **Tables & Schema** section as the single source of data when writing or verifying your solutions.
