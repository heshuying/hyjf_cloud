package com.hyjf.common.paginator;

/**
 * 分页器，根据page,limit,totalCount用于页面上分页显示多项内容，计算页码和当前页的偏移量，方便页面分页使用.
 *
 * @author badqiu
 * @author miemiedev
 */
public class Paginator implements java.io.Serializable {
    
    private static final long serialVersionUID = 6089482156906595931L;

    /** 分页栏的DOM的ID */
    public static final String DEFAULT_PAGINATOR_BAR_ID = "paginator-bar";

    /** 分页的默认隐藏变量的id属性 */
    public static final String DEFAULT_PAGINATOR_PAGE_ID = "paginator-page";

    /** 分页的默认隐藏变量的name属性 */
    public static final String DEFAULT_PAGINATOR_PAGE_NAME = "paginatorPage";

    /** 一页显示多少条 */
    private static final int DEFAULT_SLIDERS_COUNT = 5;

    private int slidersCount = DEFAULT_SLIDERS_COUNT;

    /** 分页大小 */
    private static final int DEFAULT_LIMIT = 10;

    private int limit = DEFAULT_LIMIT;

    /** 页数 */
    private int page;

    /** 总记录数 */
    private int totalCount;

    /** 总页数 */
    private int totalPages;

    /** 开始行 */
    private int startRow;

    /** 结束行 */
    private int endRow;

    /** 偏移行 */
    private int offset;

    /** 页数条 */
    private Integer[] slider;

    /** 前一页 */
    private int prePage;

    /** 后一页 */
    private int nextPage;

    /** 是否是第一页 */
    private boolean firstPage;

    /** 是否有后一页 */
    private boolean hasNextPage;

    /** 是否有前一页 */
    private boolean hasPrePage;

    /** 是否是最后页 */
    private boolean lastPage;

    /** 是否是禁用的页 */
    private boolean disabledPage;

    /**
     * @param page
     *            当前第几页
     * @param totalCount
     *            总件数
     */
    public Paginator(int page, int totalCount) {
        super();
        this.totalCount = totalCount;
        this.page = computePageNo(page);
    }

    /**
     * @param page
     *            当前第几页
     * @param limit
     *            一页显示多少
     * @param totalCount
     *            总件数
     */
    public Paginator(int page, int totalCount, int limit) {
        super();
        this.limit = limit;
        this.totalCount = totalCount;
        this.page = computePageNo(page);
    }

    /**
     * @param page
     *            当前第几页
     * @param limit
     *            一页显示多少
     * @param slidersCount
     *            翻页标签出现多少个
     * @param totalCount
     *            总件数
     */
    public Paginator(int page, int totalCount, int limit, int slidersCount) {
        super();
        this.limit = limit;
        this.totalCount = totalCount;
        this.slidersCount = slidersCount;
        this.page = computePageNo(page);
    }

    /**
     * 取得当前页。
     */
    public int getPage() {
        return page;
    }

    /**
     * 取得最小。
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 取得总项数。
     *
     * @return 总项数
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 是否是首页（第一页），第一页页码为1
     *
     * @return 首页标识
     */
    public boolean isFirstPage() {
        this.firstPage = page <= 1;
        return firstPage;
    }

    /**
     * 是否是最后一页
     *
     * @return 末页标识
     */
    public boolean isLastPage() {
        lastPage = page >= getTotalPages();
        return lastPage;
    }

    public int getPrePage() {
        if (isHasPrePage()) {
            prePage = page - 1;
        } else {
            prePage = page;
        }
        return prePage;
    }

    public int getNextPage() {
        if (isHasNextPage()) {
            nextPage = page + 1;
        } else {
            nextPage = page;
        }
        return nextPage;
    }

    /**
     * 判断指定页码是否被禁止，也就是说指定页码超出了范围或等于当前页码。
     *
     * @param page
     *            页码
     * @return boolean 是否为禁止的页码
     */
    public boolean isDisabledPage(int page) {
        disabledPage = ((page < 1) || (page > getTotalPages()) || (page == this.page));
        return disabledPage;
    }

    /**
     * 是否有上一页
     *
     * @return 上一页标识
     */
    public boolean isHasPrePage() {
        hasPrePage = (page - 1 >= 1);
        return hasPrePage;
    }

    /**
     * 是否有下一页
     *
     * @return 下一页标识
     */
    public boolean isHasNextPage() {
        hasNextPage = (page + 1 <= getTotalPages());
        return hasNextPage;
    }

    /**
     * 开始行，可以用于oracle分页使用 (1-based)。
     */
    public int getStartRow() {
        if (getLimit() <= 0 || totalCount <= 0)
            return 0;
        startRow = page > 0 ? (page - 1) * getLimit() + 1 : 0;
        return startRow;
    }

    /**
     * 结束行，可以用于oracle分页使用 (1-based)。
     */
    public int getEndRow() {
        endRow = page > 0 ? Math.min(limit * page, getTotalCount()) : 0;
        return endRow;
    }

    /**
     * offset，计数从0开始，可以用于mysql分页使用(0-based)
     */
    public int getOffset() {
        offset = page > 0 ? (page - 1) * getLimit() : 0;
        return offset;
    }

    /**
     * 得到 总页数
     *
     * @return
     */
    public int getTotalPages() {
        if (totalCount <= 0) {
            totalPages = 0;
        }
        if (limit <= 0) {
            totalPages = 0;
        }

        int count = totalCount / limit;
        if (totalCount % limit > 0) {
            count++;
        }
        totalPages = count;
        return totalPages;
    }

    protected int computePageNo(int page) {
        return computePageNumber(page, limit, totalCount);
    }

    /**
     * 页码滑动窗口，并将当前页尽可能地放在滑动窗口的中间部位。
     *
     * @return
     */
    public Integer[] getSlider() {
        slider = slider(slidersCount);
        return slider;
    }

    /**
     * 页码滑动窗口，并将当前页尽可能地放在滑动窗口的中间部位。 注意:不可以使用 getSlider(1)方法名称，因为在JSP中会与
     * getSlider()方法冲突，报exception
     *
     * @return
     */
    public Integer[] slider(int slidersCount) {
        return generateLinkPageNumbers(getPage(), (int) getTotalPages(), slidersCount);
    }

    private static int computeLastPageNumber(int totalItems, int pageSize) {
        if (pageSize <= 0)
            return 1;
        int result = (int) (totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1);
        if (result <= 1)
            result = 1;
        return result;
    }

    private static int computePageNumber(int page, int pageSize, int totalItems) {
        if (page <= 1) {
            return 1;
        }
        if (Integer.MAX_VALUE == page || page > computeLastPageNumber(totalItems, pageSize)) { // last
                                                                                               // page
            return computeLastPageNumber(totalItems, pageSize);
        }
        return page;
    }

    private static Integer[] generateLinkPageNumbers(int currentPageNumber, int lastPageNumber, int count) {
        int avg = count / 2;

        int startPageNumber = currentPageNumber - avg;
        if (startPageNumber <= 0) {
            startPageNumber = 1;
        }

        int endPageNumber = startPageNumber + count - 1;
        if (endPageNumber > lastPageNumber) {
            endPageNumber = lastPageNumber;
        }

        if (endPageNumber - startPageNumber < count) {
            startPageNumber = endPageNumber - count + 1;
            if (startPageNumber <= 0) {
                startPageNumber = 1;
            }
        }

        java.util.List<Integer> result = new java.util.ArrayList<Integer>();
        for (int i = startPageNumber; i <= endPageNumber; i++) {
            result.add(new Integer(i));
        }
        return result.toArray(new Integer[result.size()]);
    }
}
