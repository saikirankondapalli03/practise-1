package com.educative.coderust.chapter5;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

class SerializeBT {
	private static final int MARKER = Integer.MIN_VALUE;

	public static void serialize(BinaryTreeNode node, ObjectOutputStream stream) throws java.io.IOException {
		if (node == null) {
			stream.writeInt(MARKER);
			return;
		}

		stream.writeInt(node.data);
		serialize(node.left, stream);
		serialize(node.right, stream);
	}

	public static BinaryTreeNode deserialize(ObjectInputStream stream) throws java.io.IOException {
		int val = stream.readInt();
		if (val == MARKER) {
			return null;
		}

		BinaryTreeNode node = new BinaryTreeNode(val);
		node.left = deserialize(stream);
		node.right = deserialize(stream);
		return node;
	}

	public static void main(String[] args) {
		try {
			List<Integer> input = new ArrayList<Integer>();
			input.add(100);
			input.add(50);
			input.add(200);
			input.add(25);
			input.add(75);
			input.add(125);
			input.add(350);
			BinaryTreeNode root = BinaryTree.createBST(input);

			BinaryTree.displayLevelOrder(root);

			ByteArrayOutputStream baostream = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(baostream);
			serialize(root, stream);
			stream.close();

			ByteArrayInputStream baistream = new ByteArrayInputStream(baostream.toByteArray());
			ObjectInputStream istream = new ObjectInputStream(baistream);
			BinaryTreeNode rootDeserialized = deserialize(istream);

			System.out.println("\nResult:");
			BinaryTree.displayLevelOrder(rootDeserialized);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
