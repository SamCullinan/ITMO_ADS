import javafx.util.Pair;
 
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
 
public class Main {
    private FastScanner in;
    private PrintWriter out;
 
    private int n, m = 0;
    int count = 1;
    private double A, R;
    private ArrayList<Pair<Pair<Integer, Integer>, Double>> edges = new ArrayList<>();
    private ArrayList<Pair<Pair<Integer, Integer>, Double>> usedEdges = new ArrayList<>();
    private ArrayList<Double> X = new ArrayList<>();
    private ArrayList<Double> Y = new ArrayList<>();
    private double cost1 = 0;
    private double cost2 = 0;
    private int[] parent = new int[150 * 150];
    private int[] rank = new int[150 * 150];
 
    private void make_set(int v) {
        parent[v] = v;
        rank[v] = 0;
    }
 
    private int find_set(int v) {
        if (v == parent[v]) {
            return v;
        }
        return parent[v] = find_set(parent[v]);
    }
 
    private void union_sets(int a, int b) {
        a = find_set(a);
        b = find_set(b);
        if (a != b) {
            if (rank[a] < rank[b]) {
                int c = b;
                b = a;
                a = c;
            }
            parent[b] = a;
            if (rank[a] == rank[b]) {
                rank[a]++;
            }
        }
    }
 
    public class CustomComparator implements Comparator<Pair<Pair<Integer, Integer>, Double>> {
        @Override
        public int compare(Pair<Pair<Integer, Integer>, Double> o1, Pair<Pair<Integer, Integer>, Double> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }
 
    private void input() throws IOException {
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            X.add(in.nextDouble());
        }
        for (int i = 0; i < n; i++) {
            Y.add(in.nextDouble());
        }
        R = in.nextDouble();
        A = in.nextDouble();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    edges.add(new Pair<>(new Pair<>(i, j), R * Math.sqrt((X.get(i) - X.get(j)) * (X.get(i) - X.get(j)) + (Y.get(i) - Y.get(j)) * (Y.get(i) - Y.get(j)))));
                    m++;
                }
            }
        }
        edges.sort(new CustomComparator());
        for (int i = 0; i < n; i++) {
            make_set(i);
        }
    }
 
 
    private void solve() throws IOException {
        input();
        for (int i = 0; i < m; i++) {
            int from = edges.get(i).getKey().getKey();
            int to = edges.get(i).getKey().getValue();
            double w = edges.get(i).getValue();
            if (find_set(from) != find_set(to)) {
                cost1 += w;
                union_sets(from, to);
                usedEdges.add(new Pair<>(new Pair<>(from, to), w));
            }
        }
        cost2 = cost1;
        for (int i = 0; i < usedEdges.size(); i++) {
            double w = usedEdges.get(i).getValue();
            if (w > A) {
                count++;
                cost2 -= w;
            }
        }
        output();
    }
 
    private void output() throws IOException {
        out.print(Math.min(cost1, cost2 + A * count));
    }
 
    private void run() {
        try {
            String fileName = "transport";
            in = new FastScanner(new File(fileName + ".in"));
            out = new PrintWriter(new File(fileName + ".out")); // PrintWriter(System.out) || new File("output" + ".out")
 
            solve();
 
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    class FastScanner {
        BufferedReader br;
        StringTokenizer st;
 
        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f)); //InputStreamReader(System.in) || new FileReader(f)
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
 
        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt() {
            return Integer.parseInt(next());
        }
 
        double nextDouble() {
            return Double.parseDouble(next());
        }
    }
 
    public static void main(String[] arg) {
        new Main().run();
    }
}