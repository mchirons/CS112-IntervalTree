package structures;

import java.util.*;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {
	
	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;
	
	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {
		
		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}
		
		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;
		
		// sort intervals on left and right end points
		Sorter.sortIntervals(intervalsLeft, 'l');
		Sorter.sortIntervals(intervalsRight,'r');
		
		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = Sorter.getSortedEndPoints(intervalsLeft, intervalsRight);
		
		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);
		
		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}
	
	/**
	 * Builds the interval tree structure given a sorted array list of end points.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {
		
		Queue<IntervalTreeNode> q = new Queue<IntervalTreeNode>();
		IntervalTreeNode t;
		
		for (int i = 0; i < endPoints.size(); i++){
			t = new IntervalTreeNode(endPoints.get(i), endPoints.get(i), endPoints.get(i));
			q.enqueue(t);
		}
		while (q.size != 1){
			int tempSize = q.size;
			IntervalTreeNode t1, t2;
			float v1, v2;
			while (tempSize > 1){
				t1 = q.dequeue();
				t2 = q.dequeue();
				v1 = t1.maxSplitValue;
				v2 = t2.minSplitValue;
				IntervalTreeNode n = new IntervalTreeNode(((v1 + v2)/ 2), t1.minSplitValue, t2.maxSplitValue);
				n.leftChild = t1;
				n.rightChild = t2;
				q.enqueue(n);
				tempSize = tempSize - 2;
			}
			
			if (tempSize == 1){
				q.enqueue(q.dequeue());
			}
		}
		
	
		return q.dequeue();
	}
	
	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {
	
		
		IntervalTreeNode ptr = this.root;
		
		ptr.rightIntervals = new ArrayList<Interval>();
		for (int i = 0; i < leftSortedIntervals.size(); i++, ptr = this.root){
			while (ptr != null){
				
				if (ptr.splitValue >= leftSortedIntervals.get(i).leftEndPoint && ptr.splitValue <= leftSortedIntervals.get(i).rightEndPoint){
					if (ptr.leftIntervals == null){
						ptr.leftIntervals = new ArrayList<Interval>();
					}
					ptr.leftIntervals.add(leftSortedIntervals.get(i));
					break;
				}
				else if (ptr.splitValue > leftSortedIntervals.get(i).rightEndPoint){
					ptr = ptr.leftChild;
				}
				else {
					ptr = ptr.rightChild;
				}
			}
		}
		ptr = this.root;
		for (int i = 0; i < rightSortedIntervals.size(); i++, ptr = this.root){
			while (ptr != null){
				
				if (ptr.splitValue >= rightSortedIntervals.get(i).leftEndPoint && ptr.splitValue <= rightSortedIntervals.get(i).rightEndPoint){
					if (ptr.rightIntervals == null){
						ptr.rightIntervals = new ArrayList<Interval>();
					}
					ptr.rightIntervals.add(rightSortedIntervals.get(i));
					break;
				}
				else if (ptr.splitValue > rightSortedIntervals.get(i).rightEndPoint){
					ptr = ptr.leftChild;
				}
				else{
					ptr = ptr.rightChild;
				}
			}
		}
		/*
		System.out.println(root);
		System.out.println(root.leftChild);
		System.out.println(root.leftChild.leftChild);
		System.out.println(root.leftChild.leftChild.leftChild);
		System.out.println(root.leftChild.leftChild.leftChild.leftChild);
		System.out.println(root.leftChild.leftChild.leftChild.rightChild);
		System.out.println(root.leftChild.leftChild.rightChild);
		System.out.println(root.leftChild.leftChild.rightChild.leftChild);
		System.out.println(root.leftChild.leftChild.rightChild.rightChild);
		System.out.println(root.leftChild.rightChild);
		System.out.println(root.leftChild.rightChild.leftChild);
		System.out.println(root.leftChild.rightChild.leftChild.leftChild);
		System.out.println(root.leftChild.rightChild.leftChild.rightChild);
		System.out.println(root.leftChild.rightChild.rightChild);
		System.out.println(root.leftChild.rightChild.rightChild.leftChild);
		System.out.println(root.leftChild.rightChild.rightChild.rightChild);		
		System.out.println(root.rightChild);
		System.out.println(root.rightChild.leftChild);
		System.out.println(root.rightChild.leftChild.leftChild);
		System.out.println(root.rightChild.leftChild.leftChild.leftChild);
		System.out.println(root.rightChild.leftChild.leftChild.rightChild);
		System.out.println(root.rightChild.leftChild.rightChild);
		System.out.println(root.rightChild.leftChild.rightChild.leftChild);
		System.out.println(root.rightChild.leftChild.rightChild.rightChild);
		System.out.println(root.rightChild.rightChild);
		System.out.println(root.rightChild.rightChild.leftChild);
		System.out.println(root.rightChild.rightChild.leftChild.leftChild);
		System.out.println(root.rightChild.rightChild.leftChild.rightChild);
		System.out.println(root.rightChild.rightChild.rightChild);
		System.out.println(root.rightChild.rightChild.rightChild.leftChild);
		System.out.println(root.rightChild.rightChild.rightChild.rightChild);
		*/
		
		
		
	}
	
	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {
		ArrayList<Interval> resultList = new ArrayList<Interval>();
		return query(this.root, q, resultList);
		
	}
	
	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
	
	private ArrayList<Interval> query(IntervalTreeNode r, Interval q, ArrayList<Interval> resultList){
		ArrayList<Interval> lList = r.leftIntervals;
		ArrayList<Interval> rList = r.rightIntervals;
		
		if (r.leftChild == null && r.rightChild == null){
			return resultList;
		}
		if (r.splitValue >= q.leftEndPoint && r.splitValue <= q.rightEndPoint){
			if (lList != null){
				for (Interval each : lList) {
					resultList.add(each);
				}
			}
			query(r.leftChild, q, resultList);
			query(r.rightChild, q, resultList);	
		}
		else if(r.splitValue < q.leftEndPoint){  //q lies to the right of splitvalue
			if (rList != null){
				int i = rList.size();
				while (i > 0 && intersects(rList.get(i - 1), q)){
					resultList.add(rList.get(i - 1));
					i--;
				}
			}
			query(r.rightChild, q, resultList);
		}
		else if (r.splitValue > q.rightEndPoint){ //q lies to the left of splitvalue
			if (lList != null){
				int i = 0;
				while (i < lList.size() && intersects(lList.get(i), q)){
					resultList.add(lList.get(i));
					i++;
				}
			}
			query(r.leftChild, q, resultList);
		}
		return resultList;
	}
	
	private static boolean intersects(Interval a, Interval b){
		if (a.leftEndPoint >= b.leftEndPoint){
			if (a.leftEndPoint == b.leftEndPoint){
				return true;
			}
			else if (a.leftEndPoint <= b.rightEndPoint){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (a.rightEndPoint >= b.leftEndPoint){
				return true;
			}
			else{
				return false;
			}
		}
	}
}

