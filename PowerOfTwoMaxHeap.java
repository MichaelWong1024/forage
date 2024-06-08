import java.util.Collections;
import java.util.ArrayList;

public class PowerOfTwoMaxHeap {
    private ArrayList<Integer> heap;
    private int degree;

    public PowerOfTwoMaxHeap(int degree) {
	this.heap = new ArrayList<>();
	this.degree = degree;
    }

    private int parent(int index) {
	return (index - 1) / (1 << degree);
    }

    private int child(int index, int offset) {
	return (1 << degree) * index + 1 + offset;
    }

    public void insert(int value) {
	heap.add(value);
	int index = heap.size() - 1;

	while (index > 0 && heap.get(parent(index)) < heap.get(index)) {
	    Collections.swap(heap, index, parent(index));
	    index = parent(index);
	}
    }

    public int popMax() {
	if (heap.isEmpty()) {
	    throw new IllegalStateException("Heap is empty");
	}

	int max = heap.get(0);
	heap.set(0, heap.remove(heap.size() - 1));

	int index = 0;
	while (true) {
	    int maxChildrenIdx = -1;
	    for (int i = 0; i < (1 << degree); i++) {
		int childIdx = child(index, i);
		if (childIdx >= heap.size()) {
		    break;
		}

		if (maxChildrenIdx == -1 || heap.get(childIdx) > heap.get(maxChildrenIdx)) {
		    maxChildrenIdx = childIdx;
		}
	    }

	    if (maxChildrenIdx == -1 || heap.get(index) >= heap.get(maxChildrenIdx)) {
		break;
	    }

	    Collections.swap(heap, index, maxChildrenIdx);
	    index = maxChildrenIdx;
	}
	return max;
    }
}
