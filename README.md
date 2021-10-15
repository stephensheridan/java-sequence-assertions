# java-sequence-assertions
This Java program is a simplistic demonstration of using Lambda expressions, Predicates and Functions to create common operations that can be applied to an integer array (sequence). These operations can also be used in assertion statements to state things like:

This integer array contains all positive values
assert Sequence.forAll(f, 0, f.length, positive) : "Error: contains negative values";
