
import Additionals.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int V = 40;
        Random random = new Random();
        int [][] graph = new int[V][V];
        for(int i =0; i<V;i++)
        {

            for(int j =i; j<V;j++)
            {
                graph[i][j] = random.nextInt(0,1);
                graph[j][i] = graph[i][j];
            }
            graph[i][i] = 0;
        }
        for(int i =0; i<V;i++) {
            for (int j = 0; j < V; j++) {
                System.out.println(graph[i][j]);
            }
        }




    }
    static ArrayList<Integer> create_individual(int V, int number_of_colors)
    {
        Random random = new Random();
        ArrayList<Integer> individual = new ArrayList<>();
        for(int i =0; i< V; i++)
        {
            individual.add(random.nextInt(number_of_colors));
        }

        return individual;
    }

    static ArrayList<ArrayList<Integer>> crossover(ArrayList<Integer> parent1, ArrayList<Integer>parent2, int V)
    {
        Random random = new Random();
        int position = random.nextInt(2, V-2);
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        for(int i=0;i<position+1;i++)
        {
            child1.add(parent1.get(i));
            child2.add(parent2.get(i));
        }
        for(int i=position+1;i<V;i++)
        {
            child1.add(parent2.get(i));
            child2.add(parent1.get(i));
        }
        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        answer.add(child1);
        answer.add(child2);
        return answer;

    }

    static ArrayList<Integer> mutation1(ArrayList<Integer>individual, int V, int number_of_colors)
    {
        Random random = new Random();
        double probability = 0.4;
        double check = random.nextDouble(1);
        if(check<=probability)
        {
            int position = random.nextInt(V-1);
            individual.set(position, random.nextInt(number_of_colors));
        }
        return individual;
    }
    static ArrayList<Integer> mutation2(ArrayList<Integer>individual, int V, int number_of_colors)
    {
        Random random = new Random();
        double probability = 0.2;
        double check = random.nextDouble(1);
        if(check<=probability)
        {
            int position = random.nextInt(V-1);
            individual.set(position, random.nextInt(number_of_colors));
        }
        return individual;
    }


    @Test
    public void MakeNotRandomTest()
    {
        int[][] connections= {
                //0  1  2  3  4
                 {0, 1, 1, 0, 1},
                 {1, 0, 1, 0, 1},
                 {1, 1, 0, 1, 1},
                 {0, 0, 1, 0, 1},
                 {1, 1, 1, 1, 0}
        };

//        Graph gr = new Graph(connections.length,1);
//        gr.ready_graph(connections);
//        gr.print_graph();
//        int max = gr.find_max_color();
//        System.out.println(max);
    }

}

