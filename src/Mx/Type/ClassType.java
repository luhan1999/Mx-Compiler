package Mx.Type;

import Mx.Configuration;

public class ClassType extends Type
{
    private String name;

    public ClassType(String name)
    {
        this.hyperType = HyperTypes.CLASS;
        this.name = name;
        varSize = Configuration.getRegSize();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ClassType)) return false;
        return name.equals(((ClassType) obj).name);
    }

    @Override
    public String toString() {
        return String.format("ClassType(%s)", name);
    }
}
