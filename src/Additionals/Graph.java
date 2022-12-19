package Additionals;

import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private int V;
    private ArrayList<Integer> adj[]; //Adjacency Lists
    int max_am_colors = 0;

    public int getMax_am_colors()
    {
        return max_am_colors;
    }
    public ArrayList<Integer> get_adj(int V)
    {
        return adj[V];
    }

    public void setAdj(int v, ArrayList<Integer>arr)
    {
        adj[v] = new ArrayList<Integer>(arr);
    }


    public void print_graph()
    {
        System.out.println(V);
        for(int i =0; i< V;i++) {
            if(!adj[i].isEmpty())
                for(int j : get_adj(i))
                {
                    System.out.print(j+"   ");
                }
            else
                i = V;
            System.out.println();
        }

    }

    // Constructor
    public Graph(int v) {
        V = v;
        adj = new ArrayList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new ArrayList<>();
    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }
    void changeEdge(int v, int index, int new_value) {
        adj[v].set(index,new_value);
    }

    public void make_graph()
    {
        Random random = new Random();
        for (int i =0; i<V;i++)
        {
            int power = random.nextInt(0,V-1);
            if (power>max_am_colors)
                max_am_colors=power+1;
            for(int j =0; j< power && get_adj(i).size()<=power;j++)
            {
                int con = random.nextInt(V);
                while(con==i || get_adj(i).contains(con))
                    con = random.nextInt(V);
                //проблема на майб - може бути, що кількість зв'язків в процесі сворення на вершині буде більше 30
                addEdge(i,con);
            }
        }


    }

    public int travelling_distance(Graph graph, int place_one , int place_two)
    {
        return graph.get_adj(place_one).get(place_two);
    }


    public void ready_graph(int[][]distances)
    {

        for(int i = 0; i< distances.length;i++)
        {
            setAdj(i,int_arr_to_list(distances[i]));
            if(int_arr_to_list(distances[i]).size()>max_am_colors)
                max_am_colors = int_arr_to_list(distances[i]).size();
        }


    }

    static ArrayList<Integer> int_arr_to_list(int[] arr)
    {
        ArrayList<Integer>answer = new ArrayList<>();
        for (int j : arr) {
            answer.add(j);
        }
        return answer;
    }

    int find_min_in_adj(int v, ArrayList<Integer>visited)
    {
        ArrayList<Integer> ar = new ArrayList<>(this.get_adj(v));
        int min = ar.size()*1000000;
        int pos = 0;
        for(int i=0;i<ar.size();i++)
            if(ar.get(i)<min && i!=0 && !visited.contains(i))
            {
                min = ar.get(i);
                pos = i;
            }

        return pos;
    }


    public int count_fitness(ArrayList<Integer>individual){
        int fitness = 0;
        for(int i =0; i<individual.size();i++)
        {
            for(int j=i; j<individual.size();j++)
            {
                if(individual.get(i)==individual.get(j) && get_adj(i).contains(j)) {
                    fitness += 1;
                }
            }
        }

        return fitness;
    }

    public ArrayList<ArrayList<Integer>> roulette_wheel_selection(ArrayList<ArrayList<Integer>>population)
    {
        Random random = new Random();
        int total_fitness =0;
        for(ArrayList<Integer> ind : population)
        {
            total_fitness+=1/(1+count_fitness(ind));
        }
        ArrayList<Double> cumulative_fitness = new ArrayList<>();
        double cumulative_fitness_sum = 0;
        for(int i =0; i<population.size();i++)
        {
            cumulative_fitness_sum+=1./(1+count_fitness(population.get(i)))/total_fitness;
            cumulative_fitness.add(cumulative_fitness_sum);
        }
        ArrayList<ArrayList<Integer>> new_population = new ArrayList<>();
        for(int i=0; i<population.size();i++)
        {
            double roulette = random.nextDouble(0,1);
            for(int j =0; j<population.size();j++)
            {
                if(roulette<=cumulative_fitness.get(j))
                {
                    new_population.add(population.get(j));
                    break;
                }
            }

        }
        return new_population;
    }


}
