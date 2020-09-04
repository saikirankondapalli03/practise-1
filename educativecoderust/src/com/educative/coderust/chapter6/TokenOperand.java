package com.educative.coderust.chapter6;


class TokenOperand implements Token {
	double m_value;

	public TokenOperand() {
		m_value = 0;
	}

	public TokenOperand(double d) {
		m_value = d;
	}

	void setValue(double d) {
		m_value = d;
	}

	double getValue() {
		return m_value;
	}

	public boolean isOperator() {
		return false;
	}
}