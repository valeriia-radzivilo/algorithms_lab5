
import Additionals.Graph;
import Additionals.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int V = 10;
        Graph graph = new Graph(V);
        // Make graph and fill with values
        graph.make_graph();
        graph.print_graph();
        genetic_algorithm.genetic(graph, V);


    }



    @Test
    public void MakeNotRandomTest()
    {
        int[][] connections= {
                {1, 4, 5, 7, 9},
                {9, 7, 4, 3, 0},
                {9, 6, 4},
                {1, 9, 8, 7},
                {2, 1, 0, 9, 5},
                {4, 0, 8, 7},
                {2, 9},
                {5, 3, 1, 0, 9},
                {5, 3, 9},
                {0, 1, 2, 3, 4, 6, 7, 8}
        };
        int V = connections.length;
        Graph gr = new Graph(connections.length);
        gr.ready_graph(connections);
        gr.print_graph();
        genetic_algorithm.genetic(gr, V);

    }

}

