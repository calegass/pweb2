package web.controlecarros.validation.service;

public interface UniqueValue {
	boolean isValueUnique(Object value, String fieldName) throws UnsupportedOperationException;
}
