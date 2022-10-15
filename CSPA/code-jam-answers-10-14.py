################################################################################
# Class definitions
################################################################################

class LinkedListNode:
    def __init__(self, data):
        self.data = data
        self.next = None


class LinkedList:
    def __init__(self):
        self.head = None


class BinaryTreeNode:
    def __init__(self, data):
        self.data = data
        self.left = None
        self.right = None

################################################################################
# Challenges
################################################################################

# Challenge 1
def challenge1():
    """
    For any integer i: if i is divisible by 3, print "Fizz". If i is divisible
    by 5, print "Buzz". If i is divisible by  and 5, print "FizzBuzz". Bonus: if
    i is divisible by 7 but not 3 and 5, print "Fuzz".
    """
    # There are a few ways to write this, but here is one option using Python's
    # if ... else short syntax.
    out = ""

    for i in range(1, 101):
        current = ""
        current += "Fizz" if i % 3 == 0 else ""
        current += "Buzz" if i % 5 == 0 else ""
        current += "Fuzz" if i % 7 == 0 and i % 3 != 0 and i % 5 != 0 else ""
        current += str(i) if current == "" else ""

        out += current

        # print commas and new lines
        if i < 100:
            out += ", "

            if i % 15 == 0:
                out += "\n"

    print(out)


# Challenge 2
def challenge2(s):
    """
    Reverse a string without using built-in reverse functions.
    """
    reversed = [None] * len(s) # create an array the same length as the string

    for i in range(len(s)):
        # when i = 0, len(s) - i - 1 will point to the last character
        # when i = 1, len(s) - i - 1 will point to the second to last char, etc.
        reversed[i] = s[len(s) - i - 1]

    print("".join(reversed))


# Challenge 3
def challenge3(n):
    """
    Reverse a number without converting it to a string first.
    """
    x = 0

    while n != 0:
        # step 1: x = (0 * 10) + (12345 % 10) = 5        n = 1234
        # step 2: x =  (5 * 10) + (1234 % 10) = 54       n = 123
        # step 3: x =  (54 * 10) + (123 % 10) = 543      n = 12
        # step 4: x =  (543 * 10) + (12 % 10) = 5432     n = 1
        # step 5: x =  (5432 * 10) + (1 % 10) = 54321    n = 0
        x = (x * 10) + (n % 10)
        n //= 10        # performs integer division, removing the decimal

    print(x)


# Challenge 4
def challenge4(s1, s2):
    """
    Determine whether two strings are anagrams of each other (anagrams are words
    made up of the same letters rearranged).
    """

    str1 = s1.replace(" ", "")
    str2 = s2.replace(" ", "")

    while len(str1) > 0 and len(str2) > 0:
        index = str2.find(str1[0])

        if index > -1:
            str1 = str1[1:]
            str2 = str2[:index] + str2[index + 1:]
        else:
            print(f"\"{s1}\" and \"{s2}\" are not anagrams")
            return

    print(f"\"{s1}\" and \"{s2}\" are anagrams")


# Challenge 5
def challenge5(array):
    """
    Find the second largest element in an array.
    """
    print(f"Array: {array}")
    largest = -1
    second = -1

    for i in array:
        if i > largest:
            second = largest
            largest = i
        elif i != largest and i > second:
            second = i

    print(f"The second largest number is: {second}")


# Challenge 6
def fibonacci(n):
    # This is a very inefficient function. The larger n is, the worse the
    # runtime will be! You'll learn in CS 350: Algorithms and Complexity how to
    # improve your Fibonacci sequence algorithm.
    if n < 2:
        return n

    return fibonacci(n - 1) + fibonacci(n - 2)


def challenge6(steps):
    """
    Print the Fibonacci sequence using recursion.
    """
    print(fibonacci(steps))


# Challenge 7
def challenge7(linked_list):
    """
    Reverse a linked list.
    """
    print(f"Before: {print_list(linked_list, ' -> ')}")
    prev = None
    curr = linked_list.head

    while curr:
        next = curr.next
        curr.next = prev
        prev = curr
        curr = next

    linked_list.head = prev
    print(f"After:  {print_list(linked_list, ' -> ')}")


# Challenge 8
class MyBaseClass:
    def my_func(self):
        print("My base class function!")


class MySubClass(MyBaseClass):
    def my_func(self):
        super().my_func()
        print("My subclass function!")


def challenge8():
    """
    Define a subclass that overrides one of its base class's functions, then
    call both the base class and subclass version of that function.
    """
    obj = MySubClass()
    obj.my_func()


# Challenge 9
def challenge9(n):
    """
    Determine if a number is prime in O(n/2) time (twice as fast as linear time).
    """
    # For very large numbers, this will slow down quite a bit.
    for i in range(2, n // 2):
        if n % i == 0:
            print(f"{n} is not prime")
            # use "return" to end early, but we want to see the result of the
            # next loop
            break

    # If you want to speed this algorithm up even further, you can take the
    # square root of the number instead using the "math" library.
    import math

    # math.floor() rounds the decimal place down regardless of whether it's over
    # or under 0.5
    for i in range(2, math.floor(math.sqrt(n) + 1)):
        if n % i == 0:
            print(f"{n} is not prime")
            return

    print(f"{n} is prime")


# Challenge 10
def max_depth(root):
    if not root:
        return 0

    left = max_depth(root.left) + 1
    right = max_depth(root.right) + 1

    return left if left > right else right


def challenge10(root):
    """
    Find the maximum depth of a binary tree.
    """
    print(print_tree(root))
    print(f"Max depth: {max_depth(root)}")


# Challenge 11
def challenge11(linked_list, n):
    """
    Swap two nodes in a linked list.
    """
    # You could have implemented this a few ways, but I am going to write a
    # function that swaps the nth node with the next node.
    print(f"Before: {print_list(linked_list, ' -> ')}")

    i = 1
    current = linked_list.head
    prev = None

    # loop through until we reach the given node
    while i < n:
        prev = current
        current = current.next

        if not current or not current.next:
            print("Invalid n given")
            return      # we've reached the end of the list and can't swap

        i += 1

    prev.next = current.next
    current.next = current.next.next
    prev.next.next = current
    print(f"After:  {print_list(linked_list, ' -> ')}")


# Challenge 12
def challenge12(A):
    """
    Create an array B from array A, where each element B[i] = A[A[i]].
    """
    # Option 1 (Python exclusive)
    B = [A[A[i]] for i in range(len(A))]

    # Option 2
    B = [None] * len(A) # make an array the same size as A

    for i in range(len(A)):
        B[i] = A[A[i]]

    print(f"A: {A}")
    print(f"B: {B}")

################################################################################
# Main program
################################################################################

import random

class Program:
    def __init__(self):
        self.list = None
        self.tree = None
        self.A = None
        self.array = None

    def build_tree(self, root, max_height):
        """
        Build the binary tree for the challenges.
        """
        if max_height == 1:
            return

        root.left = BinaryTreeNode(random.randint(0, 9))
        root.right = BinaryTreeNode(random.randint(0, 9))

        max_height -= 1
        self.build_tree(root.left, max_height)
        self.build_tree(root.right, max_height)

    def build_challenges(self):
        """
        Build the linked list and binary tree for the challenges.
        """
        self.list = LinkedList()
        self.list.head = LinkedListNode(random.randint(0, 99))
        current = self.list.head

        for i in range(10):
            while current.next:
                current = current.next

            current.next = LinkedListNode(random.randint(0, 99))

        self.tree = BinaryTreeNode(random.randint(0, 9))
        self.build_tree(self.tree, 5)
        self.A = random.sample(range(10), 10)
        self.array = random.sample(range(100), 10)

    def run_challenge(self, n, *args):
        """
        Run a challenge by its number.
        """
        if n > 12:
            return

        print(f"CHALLENGE {n}:")
        globals()[f"challenge{n}"](*args)
        print("-" * 80)


def print_list(linked_list, delim):
    out = ""
    current = linked_list.head

    while(current):
        out += str(current.data)
        current = current.next

        if current:
            out += delim

    return out


def print_tree(root):
    if root is None:
        return

    queue = [root]
    lines = []
    line = "  "
    printed = 0
    level = 1

    while len(queue) > 0:
        current = queue.pop(0)

        if current.left is not None:
            queue.append(current.left)

        if current.right is not None:
            queue.append(current.right)

        line += f"{current.data} "
        printed += 1

        if printed % level == 0:
            lines.append(line)
            level *= 2
            printed = 0
            line = "  "

    return "\n".join([line.center(len(lines[-1])) for line in lines])


if __name__ == "__main__":
    program = Program()
    program.build_challenges()

    program.run_challenge(1)
    program.run_challenge(2, "My string")
    program.run_challenge(3, 12345)
    program.run_challenge(4, "eleven plus two", "twelve plus one")
    program.run_challenge(5, [86, 80, 46, 47, 0, 85, 14, 52, 57, 2])
    program.run_challenge(6, 25)
    program.run_challenge(7, program.list)
    program.run_challenge(8)
    program.run_challenge(9, 101)
    program.run_challenge(10, program.tree)
    program.run_challenge(11, program.list, 5)
    program.run_challenge(12, program.A)
