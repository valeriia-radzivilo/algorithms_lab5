import Additionals.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class genetic_algorithm {
    public static ArrayList<Integer> genetic(Graph graph, int V, int crossing_option, int mutation_option, int local_imp_option) {
        System.out.println("________________\n"+"NEW OPTIONS\n"+"_____________________\n");
        System.out.println("Options: ");
        System.out.println("Crossover :"+((crossing_option==1)?"with 1 point":(crossing_option==2)?"with 2 points":"with 6 points"));
        System.out.println("Mutation :"+((mutation_option==1)?"change a random gene":"swap"));
        System.out.println("Local improvement :"+((local_imp_option==1)?" change population dead individuals":" remake population"));
        int iter_all =0;
        // Upper Bound for Coloring
        int max_num_colors = graph.getMax_am_colors();
        int number_of_colors = max_num_colors;
        boolean condition = true;
        int iter = 400;
        ArrayList<Integer>found_solutions = new ArrayList<>();
        if(local_imp_option==1) localImprovement(graph);
        while (condition && number_of_colors > 0 && number_of_colors>=graph.getMin_am_col()) {
            iter_all++;
            int population_size = iter / 5;
            int generation = 0;
            ArrayList<ArrayList<Integer>> population = new ArrayList<>();
            for (int i = 0; i < population_size; i++) {
                ArrayList<Integer> individual = create_individual(V, number_of_colors);
                population.add(individual);
            }

            int best_fitness = graph.count_fitness(population.get(0));
            ArrayList<Integer> fittest_individual = new ArrayList<>(population.get(0));
            int gen = 0;
            ArrayList<Integer> fitnesses = new ArrayList<>();
            while (best_fitness != 0 && gen != iter) {
                iter_all++;
                gen += 1;
                population = new ArrayList<>(graph.tournament_selection(population));
                ArrayList<ArrayList<Integer>> new_population = new ArrayList<>();
                population_size = population.size();
                Collections.shuffle(population);
                for (int i = 0; i < population_size - 1; i += 2) {
                    ArrayList<ArrayList<Integer>> children = new ArrayList<>();
                    if (crossing_option == 1)
                        children = new ArrayList<>(crossover(population.get(i), population.get(i + 1), V));
                    else if (crossing_option == 2)
                        children = new ArrayList<>(crossover_two_point(population.get(i), population.get(i + 1), V));
                    else
                        children = new ArrayList<>(crossover_six_point(population.get(i), population.get(i + 1), V));
                    ArrayList<Integer> child1 = children.get(0);
                    ArrayList<Integer> child2 = children.get(1);
                    new_population.add(child1);
                    new_population.add(child2);
                }
                for (int i = 0; i < new_population.size(); i++) {

                    if(mutation_option == 1) new_population.set(i, new ArrayList<>(mutation_gene(population.get(i), V, number_of_colors, iter, gen)));
                    else new_population.set(i, new ArrayList<>(mutation_swap(population.get(i))));

                }
                population = new ArrayList<>(new_population);

                best_fitness = graph.count_fitness(population.get(0));
                fitnesses.add(best_fitness);
                fittest_individual = new ArrayList<>(population.get(0));
                for (ArrayList<Integer> individual : population) {
                    if (graph.count_fitness(individual) < best_fitness) {
                        best_fitness = graph.count_fitness(individual);
                        fittest_individual = new ArrayList<>(individual);
                    }
                }


                if(local_imp_option==2) localImprovement2(population,graph,max_num_colors,V);
                if (gen % 100 == 0)
                    System.out.println("Generation: " + gen + " Best_Fitness: " + best_fitness + " Individual: " + fittest_individual.toString() + "\n Col: " + number_of_colors);
            }
            System.out.println("Using: " + number_of_colors + " colors \n\n");
            if (best_fitness==0) {
                found_solutions.add(number_of_colors);
                number_of_colors-=1;

            } else
                number_of_colors -= 1;

        }
        System.out.println("Iterations: "+iter_all);
        ArrayList<Integer>answer = new ArrayList<>();
        answer.add(iter_all);
        if(found_solutions.size()>0) {
            answer.add(Collections.min(found_solutions));
            System.out.println("Graph is " + Collections.min(found_solutions) + " colorable");
        }
        else
        {
            System.out.println("Graph is" + max_num_colors+1+ " colorable");
            answer.add(max_num_colors+1);
        }
        return answer;
    }




    static ArrayList<Integer> create_individual(int V, int number_of_colors) {
        Random random = new Random();
        ArrayList<Integer> individual = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            individual.add(random.nextInt(number_of_colors));
        }

        return individual;
    }


    static ArrayList<ArrayList<Integer>> crossover(ArrayList<Integer> parent1, ArrayList<Integer> parent2, int V) {
        Random random = new Random();
        int position = random.nextInt(2, V - 2);
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        crossing_process(parent1, parent2, -1, position, child1, child2);
        crossing_process(parent2, parent1, position, V - 1, child1, child2);
        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        answer.add(child1);
        answer.add(child2);
        return answer;

    }

    static ArrayList<ArrayList<Integer>> crossover_two_point(ArrayList<Integer> parent1, ArrayList<Integer> parent2, int V) {
        Random random = new Random();

        int position = random.nextInt(0, V - 2);
        int position_two = random.nextInt(position, V - 2);
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        crossing_process(parent1, parent2, -1, position, child1, child2);
        crossing_process(parent1, parent2, position, position_two, child1, child2);
        crossing_process(parent2, parent1, position_two, V - 1, child1, child2);
        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        answer.add(child1);
        answer.add(child2);
        return answer;
    }

    static ArrayList<ArrayList<Integer>> crossover_six_point(ArrayList<Integer> parent1, ArrayList<Integer> parent2, int V) {
        Random random = new Random();
        int position = random.nextInt(0, V - 8);
        int position_two = random.nextInt(position, V - 7);
        int position_three = random.nextInt(position_two, V - 5);
        int position_four = random.nextInt(position_three, V - 3);
        int position_five = random.nextInt(position_four, V - 1);
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        crossing_process(parent1, parent2, -1, position, child1, child2);
        crossing_process(parent2, parent1, position, position_two, child1, child2);
        crossing_process(parent1, parent2, position_two, position_three, child1, child2);
        crossing_process(parent2, parent1, position_three, position_four, child1, child2);
        crossing_process(parent1, parent2, position_four, position_five, child1, child2);
        crossing_process(parent2, parent1, position_five, V - 1, child1, child2);

        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        answer.add(child1);
        answer.add(child2);
        return answer;
    }

    public static void crossing_process(ArrayList<Integer> parent1, ArrayList<Integer> parent2, int position_four, int position_five, ArrayList<Integer> child1, ArrayList<Integer> child2) {
        for (int i = position_four + 1; i < position_five + 1; i++) {
            child1.add(parent1.get(i));
            child2.add(parent2.get(i));
        }

    }

    // change of a random gene
    static ArrayList<Integer> mutation_gene(ArrayList<Integer> individual, int V, int number_of_colors, int iter, int gen) {
        Random random = new Random();
        double probability = 0.4;
        if (gen > iter / 5)
            probability = 0.6;
        double check = random.nextDouble(1);
        if (check <= probability) {
            int position = random.nextInt(V - 1);
            individual.set(position, random.nextInt(number_of_colors));
        }
        return individual;

    }

    //swap mutation
    static ArrayList<Integer> mutation_swap(ArrayList<Integer> individual) {
        Random random = new Random();
        int pos0 = random.nextInt(individual.size() - 1);
        int pos1 = random.nextInt(individual.size() - 1);
        // In swap mutation, we select two positions on the chromosome at random, and interchange the values.
        int num0 = individual.get(pos0);
        int num1 = individual.get(pos1);

        individual.set(pos0, num1);
        individual.set(pos1, num0);
        return individual;
    }

    static void localImprovement(Graph graph)
    {
        int max_colors = graph.greedyColoring()+1;
        graph.setMin_am_col(max_colors);
    }


    static void print_population(ArrayList<ArrayList<Integer>>population)
    {
        System.out.println("POPULATION: ");
        for(ArrayList<Integer>i: population)
            System.out.print(i+"  ");
        System.out.println();
    }

    static void localImprovement2(ArrayList<ArrayList<Integer>> population, Graph graph, int max_col_am,int V)
    {
        ArrayList<ArrayList<Integer>> best_of_population = new ArrayList<>();
        int amount_to_take = population.size()/2;
        int counter =0;
        for(ArrayList<Integer> p: population)
        {
            if (graph.count_fitness(p)<max_col_am-2 && counter<amount_to_take) {
                best_of_population.add(p);
                counter++;
            }
            else
                best_of_population.add(create_individual(V,max_col_am));

        }
    }

}
