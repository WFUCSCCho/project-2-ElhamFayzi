import java.io.*;
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
            if (!inputFileNameScanner.hasNextLine()) {
                System.err.println("The entered numLines is greater than the number of lines in file (" + i + " lines exist in the input file)");
                System.exit(1);
            }
            String[] line = inputFileNameScanner.nextLine().toLowerCase().split(",");
            try {
                originalPlayersList.add(new Player(line));                      // Create different player instances for each list separately to make
                copyPlayersList.add(new Player(line));                          // sure that items of both lists don't refer to the same objects.
            } catch (Exception e) {
                System.out.println("Line Number " + (i + 1) + " has an invalid format.");
            }
        }

        inputFileNameScanner.close();

        long startTime;
        long endTime;
        //============================
        Collections.sort(copyPlayersList);

        BST<Player> bstFromSortedList = new BST<Player>();
        startTime = System.nanoTime();
        populateBST(bstFromSortedList, copyPlayersList);
        endTime = System.nanoTime();
        double bstFromSortedListInsertTime = (endTime - startTime) / 1_000_000.0;

        AVLTree<Player> avlFromSortedList = new AVLTree<Player>();
        startTime = System.nanoTime();
        populateAVL(avlFromSortedList, copyPlayersList);
        endTime = System.nanoTime();
        double avlFromSortedListInsertTime = (endTime - startTime) / 1_000_000.0;


        //============================
        Collections.shuffle(copyPlayersList);

        BST<Player> bstFromRandomList = new BST<Player>();
        startTime = System.nanoTime();
        populateBST(bstFromRandomList, copyPlayersList);
        endTime = System.nanoTime();
        double bstFromRandomListInsertTime = (endTime - startTime) / 1_000_000.0;

        AVLTree<Player> avlFromRandomList = new AVLTree<Player>();
        startTime = System.nanoTime();
        populateAVL(avlFromRandomList, copyPlayersList);
        endTime = System.nanoTime();
        double avlFromRandomListInsertTime = (endTime - startTime) / 1_000_000.0;
        //=============================

        startTime = System.nanoTime();
        searchForEachPlayersBST(bstFromSortedList, originalPlayersList);
        endTime = System.nanoTime();
        double bstFromSortedListSearchTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        searchForEachPlayersAVL(avlFromSortedList, originalPlayersList);
        endTime = System.nanoTime();
        double avlFromSortedListSearchTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        searchForEachPlayersBST(bstFromRandomList, originalPlayersList);
        endTime = System.nanoTime();
        double bstFromRandomListSearchTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        searchForEachPlayersAVL(avlFromRandomList, originalPlayersList);
        endTime = System.nanoTime();
        double avlFromRandomListSearchTime = (endTime - startTime) / 1_000_000.0;

        //============================

        System.out.println("\n Insertion Times in Milliseconds for " + numLines + " lines");
        System.out.println("============================================");
        System.out.println("BST (sorted list): " + bstFromSortedListInsertTime);
        System.out.println("AVL (sorted list): " + avlFromSortedListInsertTime);
        System.out.println("BST (random list): " + bstFromRandomListInsertTime);
        System.out.println("AVL (random list): " + avlFromRandomListInsertTime);

        System.out.println("\n Search Times in Milliseconds for " + numLines + " lines");
        System.out.println("============================================");
        System.out.println("BST (sorted list): " + bstFromSortedListSearchTime);
        System.out.println("AVL (sorted list): " + avlFromSortedListSearchTime);
        System.out.println("BST (random list): " + bstFromRandomListSearchTime);
        System.out.println("AVL (random list): " + avlFromRandomListSearchTime);
        System.out.println();


        File file = new File("output.txt");
        boolean fileExists = file.exists();

        FileOutputStream outputStream = new FileOutputStream(file, true);
        PrintWriter writer = new PrintWriter(outputStream);
        if (!fileExists) {
            writer.println("numLines, BST(Sorted Insertion), AVL(SortedInsertion), BST(Random Insertion), AVL(Random Insertion), BST(Sorted Search), AVL(Sorted Search), BST(Random Search), AVL(Random Search)\n");
        }

        writer.println(numLines + "," + bstFromSortedListInsertTime + "," + avlFromSortedListInsertTime + "," + bstFromRandomListInsertTime + "," + avlFromRandomListInsertTime + "," + bstFromSortedListSearchTime + "," + avlFromSortedListSearchTime + "," + bstFromRandomListSearchTime + "," + avlFromRandomListSearchTime);

        writer.flush();
        writer.close();
    }
}
