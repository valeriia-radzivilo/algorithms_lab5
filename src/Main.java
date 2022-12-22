
import Additionals.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        int V = 300;
        Graph graph = new Graph(V);
        // Make graph and fill with values
        graph.make_graph();
        graph.print_graph();

        int min_iter = 1000000;
        int min_col= 100;
        int cross = 0;
        int mut =0;
        int local_imp =0;
        int max_colors = graph.greedyColoring()+1;
        graph.setMax_am_colors(max_colors);
        for(int i =0; i<3;i++)
        {
            for(int j =0; j<2;j++)
            {
                for(int k =0; k<2; k++)
                {
                    ArrayList<Integer> sol = genetic_algorithm.genetic(graph, V,i+1,j+1,k+1);
                    if(sol.get(0) < min_iter && sol.get(1)<=min_col)
                    {
                        min_iter = sol.get(0);
                        cross = i+1;
                        mut=j+1;
                        local_imp=k+1;
                        min_col = sol.get(1);
                    }
                }
            }
        }


        System.out.println("BEST OPTIONS ");
        System.out.println("Crossing "+ cross);
        System.out.println("Mutation "+ mut);
        System.out.println("Local Improvement "+ local_imp);
        System.out.println("Colors: "+min_col);



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

        int min_iter = 1000000;
        int min_col= 100;
        int cross = 0;
        int mut =0;
        int local_imp =0;
        for(int i =0; i<3;i++)
        {
            for(int j =0; j<2;j++)
            {
                for(int k =0; k<2; k++)
                {
                    ArrayList<Integer> sol = genetic_algorithm.genetic(gr, V,i+1,j+1,k+1);
                    if(sol.get(0) < min_iter && sol.get(1)<=min_col)
                    {
                        min_iter = sol.get(0);
                        cross = i+1;
                        mut=j+1;
                        local_imp=k+1;
                        min_col = sol.get(1);
                    }
                }
            }
        }

        System.out.println("BEST OPTIONS ");
        System.out.println("Crossing "+ cross);
        System.out.println("Mutation "+ mut);
        System.out.println("Local Improvement "+ local_imp);
        System.out.println("Colors: "+min_col);
    }    
    
    @Test
    public void MakeNotRandomTestBig()
    {
        int[][] connections= {
                {1},
                {0,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                { 1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                { 1},
                { 1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                { 1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1},
                {1}
        };
        int V = connections.length;
        Graph gr = new Graph(connections.length);
        gr.ready_graph(connections);
        gr.print_graph();
        int min_iter = 1000000;
        int min_col= 100;
        int cross = 0;
        int mut =0;
        int local_imp =0;
        for(int i =0; i<3;i++)
        {
            for(int j =0; j<2;j++)
            {
                for(int k =0; k<3; k++)
                {
                    ArrayList<Integer> sol = genetic_algorithm.genetic(gr, V,i+1,j+1,k+1);
                    if(sol.get(0) < min_iter && sol.get(1)<min_col)
                    {
                        min_iter = sol.get(0);
                        cross = i+1;
                        mut=j+1;
                        local_imp=k+1;
                        min_col = sol.get(1);
                    }
                }
            }
        }


        System.out.println("BEST OPTIONS ");
        System.out.println("Crossing "+ cross);
        System.out.println("Mutation "+ mut);
        System.out.println("Local Improvement "+ local_imp);
        System.out.println("Colors: "+min_col);

    }

    @Test
    public void TestLocalImprovement2()
    {
        for(int i =0; i<60; i++)
            if(i!=1) System.out.print(i+",");
    }

}

