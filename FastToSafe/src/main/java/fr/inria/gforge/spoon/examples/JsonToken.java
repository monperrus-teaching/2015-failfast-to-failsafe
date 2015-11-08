package fr.inria.gforge.spoon.examples;

public enum JsonToken
{

    NOT_AVAILABLE(null, JsonTokenId.ID_NOT_AVAILABLE),

    START_OBJECT("{", JsonTokenId.ID_START_OBJECT),

    END_OBJECT("}", JsonTokenId.ID_END_OBJECT),

    START_ARRAY("[", JsonTokenId.ID_START_ARRAY),

    END_ARRAY("]", JsonTokenId.ID_END_ARRAY),

    FIELD_NAME(null, JsonTokenId.ID_FIELD_NAME),

    VALUE_EMBEDDED_OBJECT(null, JsonTokenId.ID_EMBEDDED_OBJECT),

    VALUE_STRING(null, JsonTokenId.ID_STRING),

    VALUE_NUMBER_INT(null, JsonTokenId.ID_NUMBER_INT),

    VALUE_NUMBER_FLOAT(null, JsonTokenId.ID_NUMBER_FLOAT),

    VALUE_TRUE("true", JsonTokenId.ID_TRUE),

    VALUE_FALSE("false", JsonTokenId.ID_FALSE),

    VALUE_NULL("null", JsonTokenId.ID_NULL),
    ;

    final String _serialized;

    final char[] _serializedChars;

    final byte[] _serializedBytes;

    final int _id;

    final boolean _isStructStart, _isStructEnd;

    final boolean _isNumber;

    final boolean _isBoolean;

    final boolean _isScalar;

    JsonToken(String token, int id)
    {
        if (token == null) {
            _serialized = null;
            _serializedChars = null;
            _serializedBytes = null;
        } else {
            _serialized = token;
            _serializedChars = token.toCharArray();
            // It's all in ascii, can just case...
            int len = _serializedChars.length;
            _serializedBytes = new byte[len];
            for (int i = 0; i < len; ++i) {
                _serializedBytes[i] = (byte) _serializedChars[i];
            }
        }
        _id = id;

        _isBoolean = (id == JsonTokenId.ID_FALSE || id == JsonTokenId.ID_TRUE);
        _isNumber = (id == JsonTokenId.ID_NUMBER_INT || id == JsonTokenId.ID_NUMBER_FLOAT);

        _isStructStart = (id == JsonTokenId.ID_START_OBJECT || id == JsonTokenId.ID_START_ARRAY);
        _isStructEnd = (id == JsonTokenId.ID_END_OBJECT || id == JsonTokenId.ID_END_ARRAY);

        _isScalar = !_isStructStart && !_isStructEnd
                && (id != JsonTokenId.ID_FIELD_NAME)
                && (id != JsonTokenId.ID_NOT_AVAILABLE);
    }

    public final int id() { return _id; }

    public final String asString() { return _serialized; }
    public final char[] asCharArray() { return _serializedChars; }
    public final byte[] asByteArray() { return _serializedBytes; }

    public final boolean isNumeric() { return _isNumber; }

    public final boolean isStructStart() { return _isStructStart; }

    public final boolean isStructEnd() { return _isStructEnd; }

    public final boolean isScalarValue() { return _isScalar; }
    public final boolean isBoolean() { return _isBoolean; }
}
