class NoAtorException extends Exception {
    public NoAtorException () { super(); }
    public NoAtorException (String s) { super(s); }
}
class ExistingAtorException extends Exception {
    public ExistingAtorException () { super(); }
    public ExistingAtorException (String s) { super(s); }
}
class NoExistentServiceException extends Exception {
    public NoExistentServiceException () { super(); }
    public NoExistentServiceException  (String s) { super(s); }
}

class NoStoredDataException extends Exception {
    public NoStoredDataException () { super(); }
    public NoStoredDataException  (String s) { super(s); }
}

class NoSuportedException extends Exception {
    public NoSuportedException () { super(); }
    public NoSuportedException  (String s) { super(s); }
}
