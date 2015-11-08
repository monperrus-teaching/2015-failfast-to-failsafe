package fr.inria.gforge.spoon.examples;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.xml.sax.Attributes;

public class Node extends AbstractList<Object>
{
    Node _parent;
    private ArrayList<Object> _list;
    private String _tag;
    private Attribute[] _attrs;
    private boolean _lastString = false;
    private String _path;

    /* ------------------------------------------------------------ */
    Node(Node parent, String tag, Attributes attrs)
    {
        _parent = parent;
        _tag = tag;

        if (attrs != null)
        {
            _attrs = new Attribute[attrs.getLength()];
            for (int i = 0; i < attrs.getLength(); i++)
            {
                String name = attrs.getLocalName(i);
                if (name == null || name.equals(""))
                    name = attrs.getQName(i);
                _attrs[i] = new Attribute(name, attrs.getValue(i));
            }
        }
    }

    /* ------------------------------------------------------------ */
    public Node getParent()
    {
        return _parent;
    }

    /* ------------------------------------------------------------ */
    public String getTag()
    {
        return _tag;
    }

    /* ------------------------------------------------------------ */
    public String getPath()
    {
        if (_path == null)
        {
            if (getParent() != null && getParent().getTag() != null)
                _path = getParent().getPath() + "/" + _tag;
            else
                _path = "/" + _tag;
        }
        return _path;
    }

        /* ------------------------------------------------------------ */
    /**
     * Get an array of element attributes.
     * @return the attributes
     */
    public Attribute[] getAttributes()
    {
        return _attrs;
    }

        /* ------------------------------------------------------------ */
    /**
     * Get an element attribute.
     *
     * @param name the name of the attribute
     * @return attribute or null.
     */
    public String getAttribute(String name)
    {
        return getAttribute(name, null);
    }

        /* ------------------------------------------------------------ */
    /**
     * Get an element attribute.
     *
     * @param name the name of the element
     * @param dft the default value
     * @return attribute or null.
     */
    public String getAttribute(String name, String dft)
    {
        if (_attrs == null || name == null)
            return dft;
        for (int i = 0; i < _attrs.length; i++)
            if (name.equals(_attrs[i].getName()))
                return _attrs[i].getValue();
        return dft;
    }

        /* ------------------------------------------------------------ */
    /**
     * Get the number of children nodes.
     */
    public int size()
    {
        if (_list != null)
            return _list.size();
        return 0;
    }

        /* ------------------------------------------------------------ */
    /**
     * Get the ith child node or content.
     *
     * @return Node or String.
     */
    public Object get(int i)
    {
        if (_list != null)
            return _list.get(i);
        return null;
    }

        /* ------------------------------------------------------------ */
    /**
     * Get the first child node with the tag.
     *
     * @param tag the name of the tag
     * @return Node or null.
     */
    public Node get(String tag)
    {
        if (_list != null)
        {
            for (int i = 0; i < _list.size(); i++)
            {
                Object o = _list.get(i);
                if (o instanceof Node)
                {
                    Node n = (Node) o;
                    if (tag.equals(n._tag))
                        return n;
                }
            }
        }
        return null;
    }

    /* ------------------------------------------------------------ */
    @Override
    public void add(int i, Object o)
    {
        if (_list == null)
            _list = new ArrayList<Object>();
        if (o instanceof String)
        {
            if (_lastString)
            {
                int last = _list.size() - 1;
                _list.set(last, (String) _list.get(last) + o);
            }
            else
                _list.add(i, o);
            _lastString = true;
        }
        else
        {
            _lastString = false;
            _list.add(i, o);
        }
    }

    /* ------------------------------------------------------------ */
    public void clear()
    {
        if (_list != null)
            _list.clear();
        _list = null;
    }

        /* ------------------------------------------------------------ */
    /**
     * Get a tag as a string.
     *
     * @param tag The tag to get
     * @param tags IF true, tags are included in the value.
     * @param trim If true, trim the value.
     * @return results of get(tag).toString(tags).
     */
    public String getString(String tag, boolean tags, boolean trim)
    {
        Node node = get(tag);
        if (node == null)
            return null;
        String s = node.toString(tags);
        if (s != null && trim)
            s = s.trim();
        return s;
    }

    /* ------------------------------------------------------------ */
    public synchronized String toString()
    {
        return toString(true);
    }

        /* ------------------------------------------------------------ */
    /**
     * Convert to a string.
     *
     * @param tag If false, only _content is shown.
     * @return the string value
     */
    public synchronized String toString(boolean tag)
    {
        StringBuilder buf = new StringBuilder();
        toString(buf, tag);
        return buf.toString();
    }

        /* ------------------------------------------------------------ */
    /**
     * Convert to a string.
     *
     * @param tag If false, only _content is shown.
     * @param trim true to trim the content
     * @return the trimmed content
     */
    public synchronized String toString(boolean tag, boolean trim)
    {
        String s = toString(tag);
        if (s != null && trim)
            s = s.trim();
        return s;
    }

    /* ------------------------------------------------------------ */
    private synchronized void toString(StringBuilder buf, boolean tag)
    {
        if (tag)
        {
            buf.append("<");
            buf.append(_tag);

            if (_attrs != null)
            {
                for (int i = 0; i < _attrs.length; i++)
                {
                    buf.append(' ');
                    buf.append(_attrs[i].getName());
                    buf.append("=\"");
                    buf.append(_attrs[i].getValue());
                    buf.append("\"");
                }
            }
        }

        if (_list != null)
        {
            if (tag)
                buf.append(">");
            for (int i = 0; i < _list.size(); i++)
            {
                Object o = _list.get(i);
                if (o == null)
                    continue;
                if (o instanceof Node)
                    ((Node) o).toString(buf, tag);
                else
                    buf.append(o.toString());
            }
            if (tag)
            {
                buf.append("</");
                buf.append(_tag);
                buf.append(">");
            }
        }
        else if (tag)
            buf.append("/>");
    }

        /* ------------------------------------------------------------ */
    /**
     * Iterator over named child nodes.
     *
     * @param tag The tag of the nodes.
     * @return Iterator over all child nodes with the specified tag.
     */
    public Iterator<Node> iterator(final String tag)
    {
        return new Iterator<Node>()
        {
            int c = 0;
            Node _node;

            /* -------------------------------------------------- */
            public boolean hasNext()
            {
                if (_node != null)
                    return true;
                while (_list != null && c < _list.size())
                {
                    Object o = _list.get(c);
                    if (o instanceof Node)
                    {
                        Node n = (Node) o;
                        if (tag.equals(n._tag))
                        {
                            _node = n;
                            return true;
                        }
                    }
                    c++;
                }
                return false;
            }

            /* -------------------------------------------------- */
            public Node next()
            {
                try
                {
                    if (hasNext())
                        return _node;
                    throw new NoSuchElementException();
                }
                finally
                {
                    _node = null;
                    c++;
                }
            }

            /* -------------------------------------------------- */
            public void remove()
            {
                throw new UnsupportedOperationException("Not supported");
            }
        };
    }
}