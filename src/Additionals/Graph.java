package Additionals;

import java.util.*;

public class Graph {
    private int V;
    private ArrayList<Integer> adj[]; //Adjacency Lists
    int max_am_colors = 0;
    int min_am_col = 0;

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
        if(v!=w) {
            adj[v].add(w);
            adj[w].add(v);
        }
    }
    void changeEdge(int v, int index, int new_value) {
        adj[v].set(index,new_value);
    }

    public void make_graph()
    {
        Random random = new Random();
        ArrayList<Integer> list_of_top = new ArrayList<>();
        for(int i =0;i<V;i++)
            list_of_top.add(i);
        for(int i=0;i<V-10;i++)
        {
            if(list_of_top.size()>5) list_of_top.remove(list_of_top.indexOf(i));
            int power = random.nextInt(2,30); // степінь вершини
            if (power>max_am_colors)
                max_am_colors=power+1;
            if(power<min_am_col)
                min_am_col = power;
            for(int j =0; j< power && get_adj(i).size()<=power;j++)
            {
                int con = random.nextInt(list_of_top.size()-1);
                if(!get_adj(i).contains(list_of_top.get(con))&&i!=list_of_top.get(con)) addEdge(i,list_of_top.get(con));
            }


        }


    }
    public int getMin_am_col()
    {
        return min_am_col;
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

    // пропорційна селекція
//    public ArrayList<ArrayList<Integer>> roulette_wheel_selection(ArrayList<ArrayList<Integer>>population)
//    {
//        Random random = new Random();
//        int total_fitness =0;
//        for(ArrayList<Integer> ind : population)
//        {
//            total_fitness+=1/(1+count_fitness(ind));
//        }
//        ArrayList<Double> cumulative_fitness = new ArrayList<>();
//        double cumulative_fitness_sum = 0;
//        for(int i =0; i<population.size();i++)
//        {
//            cumulative_fitness_sum+=1./(1+count_fitness(population.get(i)))/total_fitness;
//            cumulative_fitness.add(cumulative_fitness_sum);
//        }
//        ArrayList<ArrayList<Integer>> new_population = new ArrayList<>();
//        for(int i=0; i<population.size();i++)
//        {
//            double roulette = random.nextDouble(0,1);
//            for(int j =0; j<population.size();j++)
//            {
//                if(roulette<=cumulative_fitness.get(j))
//                {
//                    new_population.add(population.get(j));
//                    break;
//                }
//            }
//
//        }
//        return new_population;
//    }

    public void setMax_am_colors(int max_am_colors_)
    {
        max_am_colors = max_am_colors_;
    }

    // Assigns colors (starting from 0) to all vertices and
    // prints the assignment of colors
    public int greedyColoring()
    {
        int result[] = new int[V];

        // Initialize all vertices as unassigned
        Arrays.fill(result, -1);

        // Assign the first color to first vertex
        result[0]  = 0;

        // A temporary array to store the available colors. False
        // value of available[cr] would mean that the color cr is
        // assigned to one of its adjacent vertices
        boolean available[] = new boolean[V];

        // Initially, all colors are available
        Arrays.fill(available, true);

        // Assign colors to remaining V-1 vertices
        for (int u = 1; u < V; u++)
        {
            // Process all adjacent vertices and flag their colors
            // as unavailable
            Iterator<Integer> it = adj[u].iterator() ;
            while (it.hasNext())
            {
                int i = it.next();
                if (result[i] != -1)
                    available[result[i]] = false;
            }

            // Find the first available color
            int cr;
            for (cr = 0; cr < V; cr++){
                if (available[cr])
                    break;
            }

            result[u] = cr; // Assign the found color

            // Reset the values back to true for the next iteration
            Arrays.fill(available, true);
        }

        // print the result
//        for (int u = 0; u < V; u++)
//            System.out.println("Vertex " + u + " --->  Color "
//                    + result[u]);
        int max = 0;
        for(int i =0; i<V;i++)
        {
            if(result[i]>max)
                max = result[i];
        }
        max+=1;
//        System.out.println("Graph is "+ max + " colorable");
        return max;


    }

    public ArrayList<ArrayList<Integer>> tournament_selection(ArrayList<ArrayList<Integer>>population)
    {
        ArrayList<ArrayList<Integer>> new_population = new ArrayList<>();
        for(int j =0; j<2;j++)
        {
            Collections.shuffle(population);
            for (int i =0; i<population.size()-1;i+=2)
            {
                if(count_fitness(population.get(i))<count_fitness(population.get(i+1)))
                    new_population.add(population.get(i));
                else
                    new_population.add(population.get(i+1));

                }
            }
        return new_population;
    }
        public void setMin_am_col(int min)
        {
            min_am_col = min;
        }


}



