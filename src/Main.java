import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        List<String> result = IntStream.of(1, 2, 3, 4, 5, 12, 13, 14, 19)
                .boxed()
                .collect(Collector.of(
                        () -> {
                            List<List<Integer>> list = new ArrayList<>();
                            list.add(new ArrayList<>());
                            return list;
                        },
                        (list, x) -> {
                            List<Integer> inner = list.get(list.size() - 1);
                            if (inner.size() == 0) {
                                inner.add(x);
                            } else {
                                int lastElement = inner.get(inner.size() - 1);
                                if (lastElement == x - 1) {
                                    inner.add(x);
                                } else {
                                    List<Integer> oneMore = new ArrayList<>();
                                    oneMore.add(x);
                                    list.add(oneMore);
                                }
                            }
                        },
                        (left, right) -> {
                            throw new IllegalArgumentException("No parallel!");
                        },

                        list -> {

                            return list.stream()
                                    .map(inner -> {
                                        if (inner.size() > 1) {
                                            return inner.get(0) + "-" + inner.get(inner.size() - 1);
                                        }
                                        return "" + inner.get(0);
                                    }).collect(Collectors.toList());

                        }));

        System.out.println(result);
    }

}