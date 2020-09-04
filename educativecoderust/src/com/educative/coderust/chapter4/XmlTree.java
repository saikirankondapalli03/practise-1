package com.educative.coderust.chapter4;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlTree {
	public static TreeNode createXmlTree(String xml) throws XMLStreamException {
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader reader = factory.createXMLStreamReader(is);
		Stack<TreeNode> stack = new Stack<TreeNode>();

		TreeNode last = null;
		while (reader.hasNext()) {
			if (reader.getEventType() == XMLStreamConstants.START_DOCUMENT
					|| reader.getEventType() == XMLStreamConstants.SPACE
					|| reader.getEventType() == XMLStreamConstants.END_DOCUMENT) {
				reader.next();
				continue;
			} else if (reader.getEventType() == XMLStreamConstants.END_ELEMENT) {
				if (!stack.empty()) {
					last = stack.pop();
				}
				reader.next();
				continue;
			}

			if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
				TreeNode node = new TreeNode(reader.getLocalName());

				if (!stack.empty()) {
					stack.peek().Children.add(node);
				}

				stack.push(node);
			} else if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
				TreeNode node = new TreeNode(reader.getText());

				if (!stack.empty()) {
					stack.peek().Children.add(node);
				}
			}

			reader.next();
		}
		return last;
	}

	public static void print_tree(TreeNode root, int depth) {
		if (root == null) {
			return;
		}

		for (int i = 0; i < depth; ++i)
			System.out.print("\t");
		System.out.print(root.text + "\n");
		for (TreeNode child : root.Children) {
			print_tree(child, depth + 1);
		}
	}

	public static void main(String[] args) {
		try {
			String xml = "<html>" + 
					"<body>" + 
					"<div>" + 
					"<h1>CodeRust</h1>" + 
					"<a>http://coderust.com</a> " + 
					"</div>" + 
					"<div>" + 
					"<h2>Chapter 1</h2>" + 
					"</div>" + 
					"<div>" + 
					"<h3>Chapter 2</h3>" + 
					"<h4>Chapter 2.1</h4>" + 
					"</div>" + 
					"</body>" + 
					"</html>";
			TreeNode result = createXmlTree(xml);
			print_tree(result, 0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}