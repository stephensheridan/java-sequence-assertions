/*
	Example program to demonstrate assertions over integer sequences using Lambda expressions, Functions and Predicates.
	Author: Stephen Sheridan
	Date: 15/10/2021
*/

import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.BiPredicate;


class Sequence{
	// For all statement (conjunction) that evaluates all sequence items using supplied predicate
	public static boolean forAll(int[] sequence, int rangeLower, int rangeUpper, Predicate <Integer> pred){
		boolean result = true;
		for(int i = rangeLower; i < rangeUpper; i++)
			result = result && pred.test(sequence[i]);
		return result;
	}
	// For all statement (conjunction) that does pairwise [i-1][i] comparision using the supplied bi predicate
	public static boolean forAll(int[] sequence, int rangeLower, int rangeUpper, BiPredicate <Integer, Integer> pred){
		boolean result = true;
		for(int i = rangeLower; i < rangeUpper; i++)
			result = result && pred.test(sequence[i-1], sequence[i]);
		return result;
	}
	// There exists statement (disjunction) that evaluates all sequence items using supplied predicate
	public static boolean thereExists(int[] sequence, int rangeLower, int rangeUpper, Predicate <Integer> pred){
		boolean result = false;
		for(int i = rangeLower; i < rangeUpper; i++)
			result = result || pred.test(sequence[i]);
		return result;
	}
	// Cardinality (frequency) statement that evaluates all sequence items using the supplied predicate
	// cardinality(TRUE) = 1 cardinality(FALSE) = 0
	public static int cardinality(int[] sequence, int rangeLower, int rangeUpper, Predicate <Integer> pred){
		int result = 0;
		for(int i = rangeLower; i < rangeUpper; i++)
			result = result + (pred.test(sequence[i]) ? 1 : 0);
		return result;
	}
	// Summation statement that returns the sum of all sequence items
	public static int summation(int[] sequence, int rangeLower, int rangeUpper){
		int result = 0;
		for(int i = rangeLower; i < rangeUpper; i++)
			result = result + sequence[i];
		return result;
	}
	// Product statement that returns the product of all sequence items
	public static int product(int[] sequence, int rangeLower, int rangeUpper){
		int result = 1;
		for(int i = rangeLower; i < rangeUpper; i++)
			result = result * sequence[i];
		return result;
	}
	// Min Max statement that compares sequence items (1 <= i < N) with an assumed min/max (sequence[0]) using the supplied predicate
	public static int minMax(int[] sequence, int rangeLower, int rangeUpper, BiFunction <Integer, Integer, Integer> func){
		int result = sequence[0];
		for(int i = rangeLower; i < rangeUpper; i++)
			result = func.apply(result, sequence[i]);
		return result;
	}
}

public class SequenceAssertionExample{
		
	public static void main(String[] args) {

		// Define predicates and functions that can be applied to sequences
		Predicate <Integer> positive = e -> e > 0;
		Predicate <Integer> even = e -> e % 2 == 0;
		Predicate <Integer> odd = e -> e % 2 != 0;
		Predicate <Integer> equalToSix = e -> e == 6;
		Predicate <Integer> greaterThanFour = e -> e > 4;
		BiPredicate <Integer, Integer> sortedAscending = (i, j) -> i <= j;
		BiPredicate <Integer, Integer> sortedDescending = (i, j) -> i >= j;
		BiFunction <Integer, Integer, Integer> maxFunc = (a,b) -> ((a >= b) ? a : b);
		BiFunction <Integer, Integer, Integer> minFunc = (a,b) -> ((a <= b) ? a : b);
		
		// Declare a test sequence/array
		int[] f = {2,4,6,8,10,12,14,16,18,20};

		// Ensure we have a sequence where N > 0
		assert f.length > 0 : "Error: empty sequence";

		// Does the sequence contain all positive values?
		boolean allPositive = Sequence.forAll(f, 0, f.length, positive);
		System.out.println("Contains all positive values = " + allPositive);

		// Does the sequence contain all even values?
		boolean allEven = Sequence.forAll(f, 0, f.length, even);
		System.out.println("Contains all even values = " + allEven);

		// Does the sequence contain all odd values?
		boolean allOdd = Sequence.forAll(f, 0, f.length, odd);
		System.out.println("Contains all odd values = " + allOdd);

		// Is the sequence sorted in ascending order?
		boolean sorted = Sequence.forAll(f, 1, f.length, sortedAscending);
		System.out.println("Is sorted in ascending order = " + sorted);

		// Does the sequence contain the value 6?
		boolean found = Sequence.thereExists(f, 0, f.length, equalToSix);
		System.out.println("Contains the value 6 = " + found);

		// Does the sequence contain an odd value?
		found = Sequence.thereExists(f, 0, f.length, odd);
		System.out.println("Contains an odd value = " + found);

		// What is the frequence of the values greater than 4?
		int freq = Sequence.cardinality(f, 0, f.length, greaterThanFour);
		System.out.println("Frequency of the values > 4 = " + freq);

		// What is the max value in the sequence? Assume f[0] is max to begin with
		int max = Sequence.minMax(f, 1, f.length, maxFunc);
		System.out.println("Max value = " + max);

		// What is the min value in the sequence? Assume f[0] is min to begin with
		int min = Sequence.minMax(f, 1, f.length, minFunc);
		System.out.println("Min value = " + min);

		// Use as part of an assertion like this
		assert Sequence.forAll(f, 0, f.length, positive) : "Error: contains negatives!";

	}
}