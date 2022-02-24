package pers.gxj.security.oauth2.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author xinjie_guo
 * @version 1.0.0 createTime:  2022/2/21 10:45
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {

    public static <R extends Collection<String>> R stringToCollection(String source, String split, Supplier<R> collectionFactory) {
        if (StringUtils.isNotBlank(source)) {
            return Arrays.stream(source.split(split)).collect(Collectors.toCollection(collectionFactory));
        }
        return  collectionFactory.get();
    }

    public static boolean contains(@Nullable Collection<?> collection, Object element) {
        return !isEmpty(collection) && collection.contains(element);
    }
}
