package tobyspring.hellospring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SortTest {
    Sort sort;

    @BeforeEach // 각 테스트를 실행하기 전에 선행되서 실행된다.
    void  beforeEach(){
        sort = new Sort();
        System.out.println(this); // 인스턴스가 테스트마다 매번 새롭게 만들어진다. 따라서 테스트는 독립적으로 실행된다.
    }
    @Test
    void sort(){
        // 준비 (given) beforeEach에서 준비하면 된다.

        // 실행 (when)
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));

        // 검증 (then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));
    }

    @Test
    void sort3Items(){
        // 준비 (given)

        // 실행 (when)
        List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));

        // 검증 (then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }

    @Test
    void sortAlreadySorted(){
        // 준비 (given)

        // 실행 (when)
        List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));

        // 검증 (then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }
}
