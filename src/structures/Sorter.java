package structures;

import java.util.ArrayList;

/**
 * This class is a repository of sorting methods used by the interval tree.
 * It's a utility class - all methods are static, and the class cannot be instantiated
 * i.e. no objects can be created for this class.
 * 
 * @author runb-cs112
 */
public class Sorter {

	private Sorter() { }
	
	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {
		if (intervals.isEmpty()){
			return;
		}
		if (intervals.size() == 1){
			return;
		}
		
		if (lr == 'l'){
			for (int  i = 0; i < intervals.size() - 1; i++){
				for (int j = 1; j < intervals.size(); j++){
					if (intervals.get(j).leftEndPoint < intervals.get(j - 1).leftEndPoint){
						Interval temp = intervals.get(j - 1);
						intervals.set(j - 1, intervals.get(j));
						intervals.set(j, temp);
					}
				}
			}
		}
		
		if (lr == 'r'){
			for (int  i = 0; i < intervals.size() - 1; i++){
				for (int j = 1; j < intervals.size(); j++){
					if (intervals.get(j).rightEndPoint < intervals.get(j - 1).rightEndPoint){
						Interval temp = intervals.get(j - 1);
						intervals.set(j - 1, intervals.get(j));
						intervals.set(j, temp);
					}
				}
			}
		}
	}
	
	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
		ArrayList<Integer> points = new ArrayList<Integer>();
		points.add(0, leftSortedIntervals.get(0).leftEndPoint);
		
		/*
		 * Gets left endpoints from left sorted list
		 */
		for (int i = 1; i < leftSortedIntervals.size(); i++){
			if (points.contains(leftSortedIntervals.get(i).leftEndPoint)){
				
			}
			else{
				points.add(leftSortedIntervals.get(i).leftEndPoint);
			}
		}
		
		/*
		 * Gets right endpoints from right sorted list
		 */
		for (int j = 0; j < rightSortedIntervals.size(); j++){
			if (points.contains(rightSortedIntervals.get(j).rightEndPoint)){
				
			}
			else{
				points.add(rightSortedIntervals.get(j).rightEndPoint);
			}
		}
		/*
		 * Sorts points into ascending order
		 */
		for (int  i = 0; i < points.size() - 1; i++){
			for (int j = 1; j < points.size(); j++){
				if (points.get(j) < points.get(j - 1)){
					int temp = points.get(j - 1);
					points.set(j - 1, points.get(j));
					points.set(j, temp);
				}
			}
		}
		
		return points;
	}
}
