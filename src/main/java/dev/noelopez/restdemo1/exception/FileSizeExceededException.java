package dev.noelopez.restdemo1.exception;

public class FileSizeExceededException extends RuntimeException {
    public FileSizeExceededException(int fileSize, int maxSize) {
        super("File Size "+fileSize+" is larger than "+maxSize);
    }
}
