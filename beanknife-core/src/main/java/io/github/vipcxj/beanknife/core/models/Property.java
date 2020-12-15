package io.github.vipcxj.beanknife.core.models;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.github.vipcxj.beanknife.runtime.annotations.Access;
import io.github.vipcxj.beanknife.core.utils.Utils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.io.PrintWriter;

public class Property {

    private final String name;
    private final Modifier modifier;
    private Access getter;
    private Access setter;
    private Type type;
    private TypeMirror typeMirror;
    private final boolean method;
    private final String getterName;
    private final String setterName;
    private final boolean writeable;
    private final Element element;
    private final String comment;
    private Extractor extractor;
    private DeclaredType converter;
    private DeclaredType viewTarget;

    public Property(
            String name,
            Modifier modifier,
            Access getter,
            Access setter,
            Type type,
            boolean method,
            String getterName,
            String setterName,
            boolean writeable,
            Element element,
            String comment
    ) {
        this.name = name;
        this.modifier = modifier;
        this.getter = getter;
        this.setter = setter;
        this.type = type;
        this.method = method;
        this.getterName = getterName;
        this.setterName = setterName;
        this.writeable = writeable;
        this.element = element;
        this.comment = comment;
        if (element.getKind() == ElementKind.FIELD) {
            this.typeMirror = element.asType();
        } else if (element.getKind() == ElementKind.METHOD) {
            this.typeMirror = ((ExecutableElement) element).getReturnType();
        } else {
            throw new IllegalStateException("This is impossible!");
        }
    }

    public Property(Property other, String commentIfNone) {
        this.name = other.name;
        this.modifier = other.modifier;
        this.getter = other.getter;
        this.setter = other.setter;
        this.type = other.type;
        this.method = other.method;
        this.getterName = other.getterName;
        this.setterName = other.setterName;
        this.writeable = other.writeable;
        this.element = other.element;
        this.typeMirror = other.typeMirror;
        this.comment = other.comment != null ? other.comment : commentIfNone;
        this.extractor = other.extractor;
        this.converter = other.converter;
        this.viewTarget = other.viewTarget;
    }

    public Property withGetterAccess(Access access) {
        Property property = new Property(this, null);
        property.getter = access;
        return property;
    }

    public Property withSetterAccess(Access access) {
        Property property = new Property(this, null);
        property.setter = access;
        return property;
    }

    public Property withExtractor(Extractor extractor) {
        Property property = new Property(this, null);
        property.extractor = extractor;
        return property;
    }

    public Property withType(@NonNull TypeMirror type, @CheckForNull DeclaredType viewTarget) {
        Property property = new Property(this, null);
        property.type = Type.extract(type);
        property.typeMirror = type;
        property.viewTarget = viewTarget;
        return property;
    }

    public Property withConverter(DeclaredType converter) {
        Property property = new Property(this, null);
        property.converter = converter;
        return property;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public String getName() {
        return name;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public Access getGetter() {
        return getter;
    }

    public boolean hasGetter() {
        return getter != Access.NONE;
    }

    public Access getSetter() {
        return setter;
    }

    public boolean hasSetter() {
        return setter != Access.NONE;
    }

    public Type getType() {
        return type;
    }

    public boolean isMethod() {
        return method;
    }

    public String getGetterName() {
        return getterName;
    }

    public String getSetterName() {
        return setterName;
    }

    public Element getElement() {
        return element;
    }

    public String getComment() {
        return comment;
    }

    public Extractor getExtractor() {
        return extractor;
    }

    public DeclaredType getConverter() {
        return converter;
    }

    public DeclaredType getViewTarget() {
        return viewTarget;
    }

    public boolean isDynamic() {
        return extractor != null && extractor.isDynamic();
    }

    public boolean isCustomMethod() {
        return extractor instanceof StaticMethodExtractor;
    }

    public void printType(@NonNull PrintWriter writer, @NonNull Context context, boolean generic, boolean withBound) {
        type.printType(writer, context, generic, withBound);
    }

    public void printField(@NonNull PrintWriter writer, @NonNull Context context, String indent, int indentNum) {
        Utils.printIndent(writer, indent, indentNum);
        writer.print("private ");
        printType(writer, context, true, false);
        writer.print(" ");
        writer.print(context.getMappedFieldName(this));
        writer.println(";");
    }

    public void printGetter(@NonNull PrintWriter writer, @NonNull Context context, String indent, int indentNum) {
        if (!hasGetter()) return;
        Utils.printComment(writer, comment, indent, indentNum);
        Utils.printIndent(writer, indent, indentNum);
        Utils.printAccess(writer, getter);
        printType(writer, context, true, false);
        writer.print(" ");
        writer.print(getGetterName());
        writer.println("() {");
        Utils.printIndent(writer, indent, indentNum);
        writer.print(indent);
        writer.print("return ");
        if (isDynamic()) {
            extractor.print(writer, context);
        } else {
            writer.print("this.");
            writer.print(context.getMappedFieldName(this));
        }
        writer.println(";");
        Utils.printIndent(writer, indent, indentNum);
        writer.println("}");
    }

    public void printSetter(@NonNull PrintWriter writer, @NonNull Context context, String indent, int indentNum) {
        if (!hasSetter()) return;
        Utils.printIndent(writer, indent, indentNum);
        Utils.printAccess(writer, setter);
        writer.print("void ");
        writer.print(getSetterName());
        writer.print("(");
        printType(writer, context, true, false);
        writer.print(" ");
        String mappedFieldName = context.getMappedFieldName(this);
        writer.print(mappedFieldName);
        writer.println(") {");
        Utils.printIndent(writer, indent, indentNum + 1);
        writer.print("this.");
        writer.print(mappedFieldName);
        writer.print(" = ");
        writer.print(mappedFieldName);
        writer.println(";");
        Utils.printIndent(writer, indent, indentNum);
        writer.println("}");
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", modifier=" + modifier +
                ", type=" + type +
                ", method=" + method +
                ", getterName='" + getterName + '\'' +
                ", element=" + element +
                ", comment='" + comment + '\'' +
                '}';
    }
}