package controller;

import model.value.IValue;
import model.value.ReferenceValue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;

public final class GarbageCollector {

    private GarbageCollector() {}

    public static Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> ((ReferenceValue) v).address())
                .collect(Collectors.toSet());
    }

    public static Set<Integer> getReachableAddresses(Set<Integer> rootAddresses,
                                                     Map<Integer, IValue> heapMap) {
        Set<Integer> reachable = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>(rootAddresses);

        while (!stack.isEmpty()) {
            Integer address = stack.pop();
            if (address == 0 || !heapMap.containsKey(address)) continue;
            if (!reachable.add(address)) continue; // already processed

            IValue val = heapMap.get(address);
            if (val instanceof ReferenceValue refVal) {
                Integer innerAddress = refVal.address();
                // only follow if the heap contains that address (otherwise it's dangling)
                if (innerAddress != 0 && heapMap.containsKey(innerAddress) && !reachable.contains(innerAddress)) {
                    stack.push(innerAddress);
                }
            }
        }

        return reachable;
    }

    public static Map<Integer, IValue> safeGarbageCollector(Collection<Integer> symTableAddr,
                                                            Map<Integer, IValue> heap) {
        Set<Integer> roots = new HashSet<>(symTableAddr);
        Set<Integer> reachable = getReachableAddresses(roots, heap);
        return heap.entrySet().stream()
                .filter(e -> reachable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
