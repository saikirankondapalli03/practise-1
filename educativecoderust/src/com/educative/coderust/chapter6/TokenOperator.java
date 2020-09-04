package com.educative.coderust.chapter6;

class TokenOperator implements Token {
	char m_value;

	public TokenOperator() {
		m_value = 0;
	}

	public TokenOperator(char d) {
		m_value = d;
	}

	public void setValue(char d) {
		m_value = d;
	}

	char getValue() {
		return m_value;
	}

	public boolean isOperator() {
		return true;
	}
}

