package io.github.vipcxj.beanknife.cases.beans;

import io.github.vipcxj.beanknife.runtime.annotations.internal.GeneratedView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

@GeneratedView(targetClass = ViewPropertyBean.class, configClass = ViewPropertyBeanViewConfig.class)
public class ViewPropertyBeanWithoutParent {

    private int a;

    private String b;

    private ViewPropertyContainerBean parent;

    public ViewPropertyBeanWithoutParent() { }

    public ViewPropertyBeanWithoutParent(
        int a,
        String b,
        ViewPropertyContainerBean parent
    ) {
        this.a = a;
        this.b = b;
        this.parent = parent;
    }

    public ViewPropertyBeanWithoutParent(ViewPropertyBeanWithoutParent source) {
        this.a = source.a;
        this.b = source.b;
        this.parent = source.parent;
    }

    public static ViewPropertyBeanWithoutParent read(ViewPropertyBean source) {
        if (source == null) {
            return null;
        }
        ViewPropertyBeanWithoutParent out = new ViewPropertyBeanWithoutParent();
        out.a = source.getA();
        out.b = source.getB();
        out.parent = source.getParent();
        return out;
    }

    public static ViewPropertyBeanWithoutParent[] read(ViewPropertyBean[] sources) {
        if (sources == null) {
            return null;
        }
        ViewPropertyBeanWithoutParent[] results = new ViewPropertyBeanWithoutParent[sources.length];
        for (int i = 0; i < sources.length; ++i) {
            results[i] = read(sources[i]);
        }
        return results;
    }

    public static List<ViewPropertyBeanWithoutParent> read(List<ViewPropertyBean> sources) {
        if (sources == null) {
            return null;
        }
        List<ViewPropertyBeanWithoutParent> results = new ArrayList<>();
        for (ViewPropertyBean source : sources) {
            results.add(read(source));
        }
        return results;
    }

    public static Set<ViewPropertyBeanWithoutParent> read(Set<ViewPropertyBean> sources) {
        if (sources == null) {
            return null;
        }
        Set<ViewPropertyBeanWithoutParent> results = new HashSet<>();
        for (ViewPropertyBean source : sources) {
            results.add(read(source));
        }
        return results;
    }

    public static Stack<ViewPropertyBeanWithoutParent> read(Stack<ViewPropertyBean> sources) {
        if (sources == null) {
            return null;
        }
        Stack<ViewPropertyBeanWithoutParent> results = new Stack<>();
        for (ViewPropertyBean source : sources) {
            results.add(read(source));
        }
        return results;
    }

    public static <K> Map<K, ViewPropertyBeanWithoutParent> read(Map<K, ViewPropertyBean> sources) {
        if (sources == null) {
            return null;
        }
        Map<K, ViewPropertyBeanWithoutParent> results = new HashMap<>();
        for (Map.Entry<K, ViewPropertyBean> source : sources.entrySet()) {
            results.put(source.getKey(), read(source.getValue()));
        }
        return results;
    }

    public int getA() {
        return this.a;
    }

    public String getB() {
        return this.b;
    }

    public ViewPropertyContainerBean getParent() {
        return this.parent;
    }

}