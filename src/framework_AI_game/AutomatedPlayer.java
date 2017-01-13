/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework_AI_game;

import graph.Vertex;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author user
 */
public class AutomatedPlayer extends Player {

    public AutomatedPlayer(String name, List<Move> available_moves, Game game, Turn playerType) {
        super(name, available_moves, game, playerType);
    }

    @Override
    public State doMove(State currentState, Move mover) {

        // Set the other player to play next turn
        getCurrentGamePlaying().setCurrentPlayer(getCurrentGamePlaying().getOtherPlayer());

        return (State) enhancedAStarSearch((Vertex) currentState);
    }

    /**
     * Search the current problem using the enhanced A* search
     *
     * @param start
     * @return the stack containing all possible moves to get to goal if not
     * found goal, return null
     */
    public Vertex enhancedAStarSearch(Vertex start) {

        // Computer's winning state
        State winningState = getCurrentGamePlaying().getComputerWinState();

        //Comparator used for the priority queue operation
        Comparator comp = (Comparator) (Object o1, Object o2) -> {
            //int d1 = ((Vertex) o1).getDistance();
            int h1 = ((State) o1).getHeuristic(getCurrentGamePlaying().getComputerWinState());

            //int d2 = ((Vertex) o2).getDistance();
            int h2 = ((State) o2).getHeuristic(getCurrentGamePlaying().getComputerWinState());

            return (h1) - (h2);
        };

        //The stack containing the solution moves
        Stack<Vertex> solution = new Stack<>();

        //Create 2 hash table that will be used for maintining the opened and closed vertices
        HashMap<State, Vertex> opened = new HashMap<>();
        HashMap<State, Vertex> closed = new HashMap<>();

        //Create the initial priority queue
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(1, comp);

        //Push start state to the priority queue
        priorityQueue.add(start);
        opened.put((State) start, start);

        while (!priorityQueue.isEmpty()) {

            //Pop out the vertex
            Vertex toBeExpanded = priorityQueue.remove();

            //Remove it from opened hash table
            opened.remove((State) toBeExpanded);
            closed.put((State) toBeExpanded, toBeExpanded);

            //Check if the vertex is final state
            if (((State) toBeExpanded).equals(winningState)) {

                //Push the final state onto the solution stack
                solution.push(toBeExpanded);

                //Keep pushing all predecessors onto the solution stack until it reach the start state
                for (Vertex pred = toBeExpanded.getPredecessor(); !pred.equals(start);) {
                    solution.push(pred);
                    pred = pred.getPredecessor();
                }

                // Return computer's next move
                return solution.pop();

            } else { //If not, expand it

                //Expand the node, depend on the algorithms that it will expand in differnt way
                List<Vertex> children = enhancedExpand(toBeExpanded);
                Iterator<Vertex> iter = children.iterator();

                //Add the children to the priorityQueue
                while (iter.hasNext()) {

                    Vertex child = iter.next();

                    if (opened.containsKey((State) child)) {

                        if (child.getDistance() < opened.get((State) child).getDistance()) {

                            //Get the node about to be promoted
                            Vertex promotedNode = opened.get(((State) child));

                            //Set new distance
                            promotedNode.setDistance(child.getDistance());

                            //Set new predecessor
                            promotedNode.setPredecessor(child.getPredecessor());

                            //Promote the node in the priority queue
                            priorityQueue.remove(opened.get((State) child));
                            priorityQueue.add(promotedNode);

                        }
                    } else if (closed.containsKey((State) child)) {
                        if (child.getDistance() < closed.get((State) child).getDistance()) {

                            Vertex restoredNode = closed.get((State) child);

                            //Set new distance
                            restoredNode.setDistance(child.getDistance());

                            //Set new predecessor
                            restoredNode.setPredecessor(child.getPredecessor());

                            //Restore the state
                            priorityQueue.add(restoredNode);
                            opened.put((State) restoredNode, restoredNode);
                            closed.remove((State) child);

                        }
                    } else {

                        priorityQueue.add(child);
                        opened.put((State) child, child);
                    }
                }
            }

        }

        // Return null if computer cannot figure out next move
        return null;
    }

    /**
     * Expands a vertex v in a state space search tree by creating a list (its
     * children) of all vertices adjacent to it in the state space. The list may
     * not include any vertex on the predecessor path leading to v. Each child
     * on the list has its predecessor set to v and its distance from the root
     * (its depth in the search tree) set to one more than v's distance.
     *
     * <b>NOTE</b> This enhanced expand method is used by the enhanced A* search
     *
     * @param v the vertex being expanded
     * @return a list of the children
     */
    private List<Vertex> enhancedExpand(Vertex v) {

        List<Vertex> expandedVertices = new LinkedList<>();

        State parent = (State) v;

        List<Move> availableMove = this.getMoves();
        Iterator<Move> iter = availableMove.iterator();

        while (iter.hasNext()) {
            Vertex child = (Vertex) (iter.next().doMove(parent));

            /* It doesnt check for occured on path because the enhanced A*
             * search maintains 2 hash table for checking duplicates
             */
            if (child != null && !child.equals(v)) {

                child.setPredecessor(v);
                child.setDistance(v.getDistance() + 1);
                expandedVertices.add(child);
            }
        }

        return expandedVertices;
    }
}
