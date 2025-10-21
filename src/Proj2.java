import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj2 {
    public static void populateBST(BST<Player> BST, ArrayList<Player> players) {
        for (Player player : players) {
            BST.insert(player);
        }
    }

    public static void populateAVL(AVLTree<Player> AVLTree, ArrayList<Player> players) {
        for (Player player : players) {
            AVLTree.insert(player);
        }
    }

    public static void searchForEachPlayersBST(BST<Player> BST, ArrayList<Player> players) {
        for (Player player : players) {
            BST.search(player);
        }
    }

    public static void searchForEachPlayersAVL(AVLTree<Player> AVLTree, ArrayList<Player> players) {
        for (Player player : players) {
            AVLTree.contains(player);
        }
    }


    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

	// FINISH ME
        ArrayList<Player> originalPlayersList = new ArrayList<Player>();
        ArrayList<Player> copyPlayersList = new ArrayList<Player>();

        for (int i = 0; i < numLines; i++) {

            String[] line = inputFileNameScanner.nextLine().toLowerCase().split(",");
            Player player;
            try {
                player = new Player(line);
                originalPlayersList.add(player);
                copyPlayersList.add(player);
            } catch (Exception e) {
                System.out.println("Line Number " + (i + 1) + " has an invalid format.");
            }
        }

        //============================
        Collections.sort(copyPlayersList);

        BST<Player> sortedBST = new BST<Player>();
        populateBST(sortedBST, copyPlayersList);

        AVLTree<Player> sortedAVLTree = new AVLTree<Player>();
        populateAVL(sortedAVLTree, copyPlayersList);

        //============================
        Collections.shuffle(copyPlayersList);
        BST<Player> randomBST = new BST<Player>();
        populateBST(randomBST, copyPlayersList);

        AVLTree<Player> randomAVLTree = new AVLTree<Player>();
        populateAVL(randomAVLTree, copyPlayersList);
        //=============================

        searchForEachPlayersBST(sortedBST, originalPlayersList);
        searchForEachPlayersAVL(sortedAVLTree, originalPlayersList);
        searchForEachPlayersBST(randomBST, originalPlayersList);
        searchForEachPlayersAVL(randomAVLTree, originalPlayersList);


    }
}
