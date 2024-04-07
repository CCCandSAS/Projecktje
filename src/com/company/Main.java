package com.company;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final int ELEMENT_SIZE = 50;
    private static final int ELEMENT_GAP = 20;

    private JComboBox<String> sortComboBox;
    private JButton sortButton;
    private JPanel drawingPanel;

    private List<SortableElement> elements;

    public Main() {
        setTitle("Sorting Visualization");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();
        initializeElements();

        setVisible(true);
    }

    private void initializeComponents() {
        JPanel controlPanel = new JPanel();
        sortComboBox = new JComboBox<>(new String[]{"Bubble sort", "Selection sort", "Insertion sort", "Merge sort", "Quick sort", "Heap sort"});
        sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> sortElements());
        controlPanel.add(sortComboBox);
        controlPanel.add(sortButton);

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (SortableElement element : elements) {
                    element.draw(g);
                }
            }
        };
        drawingPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(drawingPanel, BorderLayout.CENTER);
    }

    private void initializeElements() {
        elements = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int x = i * (ELEMENT_SIZE + ELEMENT_GAP) + 50;
            elements.add(new SortableElement(x, 50, (i + 1) * ELEMENT_SIZE, (i + 1) * ELEMENT_SIZE));
        }
    }

    private void sortElements() {
        String selectedSort = (String) sortComboBox.getSelectedItem();
        switch (selectedSort) {
            case "Bubble sort":
                bubbleSort();
                break;
            case "Selection sort":
                selectionSort();
                break;
            case "Insertion sort":
                insertionSort();
                break;
            case "Merge sort":
                mergeSort(elements, 0, elements.size() - 1);
                break;
            case "Quick sort":
                quickSort(elements, 0, elements.size() - 1);
                break;
            case "Heap sort":
                heapSort(elements);
                break;
            default:
                break;
        }
    }

    private void bubbleSort() {
        for (int i = 0; i < elements.size() - 1; i++) {
            for (int j = 0; j < elements.size() - i - 1; j++) {
                if (elements.get(j).getSize() > elements.get(j + 1).getSize()) {
                    // Swap elements
                    Collections.swap(elements, j, j + 1);

                    // Repaint after each swap
                    drawingPanel.repaint();
                    try {
                        Thread.sleep(100); // Delay for visualization
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void selectionSort() {
        for (int i = 0; i < elements.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < elements.size(); j++) {
                if (elements.get(j).getSize() < elements.get(minIndex).getSize()) {
                    minIndex = j;
                }
            }
            Collections.swap(elements, i, minIndex);

            // Repaint after each swap
            drawingPanel.repaint();
            try {
                Thread.sleep(100); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertionSort() {
        for (int i = 1; i < elements.size(); i++) {
            SortableElement key = elements.get(i);
            int j = i - 1;
            while (j >= 0 && elements.get(j).getSize() > key.getSize()) {
                elements.set(j + 1, elements.get(j));
                j--;
            }
            elements.set(j + 1, key);

            // Repaint after each swap
            drawingPanel.repaint();
            try {
                Thread.sleep(100); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void merge(List<SortableElement> arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        List<SortableElement> L = new ArrayList<>(n1);
        List<SortableElement> R = new ArrayList<>(n2);

        for (int i = 0; i < n1; ++i)
            L.add(arr.get(l + i));
        for (int j = 0; j < n2; ++j)
            R.add(arr.get(m + 1 + j));

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L.get(i).getSize() <= R.get(j).getSize()) {
                arr.set(k, L.get(i));
                i++;
            } else {
                arr.set(k, R.get(j));
                j++;
            }
            k++;

            // Repaint after each swap
            drawingPanel.repaint();
            try {
                Thread.sleep(100); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (i < n1) {
            arr.set(k, L.get(i));
            i++;
            k++;

            // Repaint after each swap
            drawingPanel.repaint();
            try {
                Thread.sleep(100); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (j < n2) {
            arr.set(k, R.get(j));
            j++;
            k++;

            // Repaint after each swap
            drawingPanel.repaint();
            try {
                Thread.sleep(100); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void mergeSort(List<SortableElement> arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    private int partition(List<SortableElement> arr, int low, int high) {
        SortableElement pivot = arr.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr.get(j).getSize() < pivot.getSize()) {
                i++;

                Collections.swap(arr, i, j);

                // Repaint after each swap
                drawingPanel.repaint();
                try {
                    Thread.sleep(100); // Delay for visualization
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Collections.swap(arr, i + 1, high);

        // Repaint after each swap
        drawingPanel.repaint();
        try {
            Thread.sleep(100); // Delay for visualization
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return i + 1;
    }

    private void quickSort(List<SortableElement> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private void heapify(List<SortableElement> arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr.get(l).getSize() > arr.get(largest).getSize())
            largest = l;

        if (r < n && arr.get(r).getSize() > arr.get(largest).getSize())
            largest = r;

        if (largest != i) {
            Collections.swap(arr, i, largest);

            // Repaint after each swap
            drawingPanel.repaint();
            try {
                Thread.sleep(100); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            heapify(arr, n, largest);
        }
    }

    private void heapSort(List<SortableElement> arr) {
        int n = arr.size();

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        for (int i = n - 1; i > 0; i--) {
            Collections.swap(arr, 0, i);

            // Repaint after each swap
            drawingPanel.repaint();
            try {
                Thread.sleep(100); // Delay for visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            heapify(arr, i, 0);
        }
    }

    private class SortableElement {
        private int x;
        private int y;
        private int width;
        private int height;

        public SortableElement(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public int getSize() {
            return width * height;
        }

        public void draw(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(x, y, width, height);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
