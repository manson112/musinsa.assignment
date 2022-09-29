package manson112.github.musinsa.assignment.config.web;


import org.springframework.util.Assert;

public class DefaultPageRequest implements Pageable {
    private final long offset;
    private final int size;

    public DefaultPageRequest() {
        this(0, 20);
    }

    public DefaultPageRequest(long offset, int size) {
        Assert.isTrue(offset >= 0, "offset must be greater or equals to 0");
        Assert.isTrue(size >= 1, "size must be greater then 0");

        this.offset = offset;
        this.size   = size;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public int getSize() {
        return size;
    }
}
