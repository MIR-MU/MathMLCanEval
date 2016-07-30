package cz.muni.fi.mir.mathmlcaneval.database;

import org.hibernate.dialect.HSQLDialect;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 30.07.2016.
 */
public class ImprovedHSQLDialect extends HSQLDialect
{
    @Override
    public String getDropSequenceString(String sequenceName)
    {
        return "drop sequence if exists " + sequenceName;
    }

    @Override
    public boolean dropConstraints()
    {
        return false;
    }

    @Override
    public boolean supportsIfExistsBeforeTableName()
    {
        return true;
    }

    @Override
    public boolean supportsIfExistsAfterTableName()
    {
        return false;
    }

    @Override
    public String getCascadeConstraintsString()
    {
        return " CASCADE ";
    }
}
