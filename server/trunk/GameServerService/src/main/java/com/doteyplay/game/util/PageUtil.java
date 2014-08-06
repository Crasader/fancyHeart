package com.doteyplay.game.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ҳ������, ���ṩ�˶�list��array������
 * ��ȡ��ҳ��:
 * @see getTotalPageNum(int totalRecordNum, int perPageRecordNum)
 * ��ҳ������
 * @see getCurrentPageList(T[] arrays, int curPageNum)
 * @see getCurrentPageList(List<T> list, int curPageNum, int perPageRecordNum)
 * �����Ҫʹ�ô����������ֻ��ʹ�������ṩ�ķ�ҳ������
 * ��Щ��ҳ������overload.��֧��ʹ��һ��ָ����ÿҳ��¼�������з�ҳ����
 * ͬʱ��Ҳ������һ��Ĭ�ϴ�С��ÿҳ��¼���򷽷���
 */
public final class PageUtil {
	
	/**
	 * ������
	 */
	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	/**
	 * ��LIST
	 */
	@SuppressWarnings("rawtypes")
	private static final List EMPTY_LIST = new ArrayList(0);
	
	/**
	 * Ĭ��ÿҳ�ܼ�¼��
	 */
	private static final byte DEFAULT_PER_PAGE_RECORD_NUM = 20;
		
	private PageUtil() {}
	
	
	/**
	 * ��ȡ��ҳ��
	 * @param totalRecordNum
	 * @return
	 * int 
	 * author luowei
	 */
	public static int getTotalPageNum(int totalRecordNum) {
		return getTotalPageNum(totalRecordNum, PageUtil.DEFAULT_PER_PAGE_RECORD_NUM);
	}
	
	/**
	 * �����ҳ��
	 * @author luowei
	 * @param totalRecordNum
	 * @param perPageRecordNum
	 * void
	 */
	public static int getTotalPageNum(int totalRecordNum, int perPageRecordNum) {
		//�ܼ�¼��С��ÿҳ��¼��
		if (totalRecordNum < perPageRecordNum) {
			return 1;
		}
		//�������
		if (totalRecordNum % perPageRecordNum == 0) {
			return totalRecordNum / perPageRecordNum;
		}
		return totalRecordNum / perPageRecordNum + 1;
	}
	
	public static <T> T[] getCurrentPageList(T[] arrays, int curPageNum) {
		return getCurrentPageList(arrays, curPageNum, PageUtil.DEFAULT_PER_PAGE_RECORD_NUM);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] getCurrentPageList(T[] arrays, 
			int curPageNum, int perPageRecordNum) {
		assertNULL(arrays);
		int totalRecordNum = arrays.length;
		int totalPageNum = getTotalPageNum(totalRecordNum, perPageRecordNum);
		int beginIndex = getBeginIndex(totalPageNum, curPageNum, perPageRecordNum);		
		if (isValidBoundsOfBeginIndex(beginIndex, totalRecordNum)) {
			return (T[]) EMPTY_ARRAY;
		}
		int endIndex = getEndIndex(totalPageNum, totalRecordNum, beginIndex);
		
		@SuppressWarnings("rawtypes")
		Class c = arrays.getClass();
		T[] newArray = (T[]) createNewArray(c, endIndex - beginIndex + 1);
		System.arraycopy(arrays, beginIndex, newArray, 0, newArray.length);
		
		return newArray;
	}
	
	/**
	 * ��ȡ��ǰҳ
	 * @param <T>
	 * @param list
	 * @param curPageNum
	 * @param perPageRecordNum ÿҳ��¼��
	 * @return
	 * List<T> 
	 * author luowei
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getCurrentPageList(List<T> list,
			int curPageNum, int perPageRecordNum) {
		assertNULL(list);
		int totalRecordNum = list.size();
		int totalPageNum = getTotalPageNum(totalRecordNum, perPageRecordNum);
		int beginIndex = getBeginIndex(totalPageNum, curPageNum, perPageRecordNum);		
		if (isValidBoundsOfBeginIndex(beginIndex, totalRecordNum)) {
			return EMPTY_LIST;
		}
		int endIndex = getEndIndex(perPageRecordNum, totalRecordNum, beginIndex);
		
		return list.subList(beginIndex, endIndex);
	}
	
	public static <T> List<T> getCurrentPageList(List<T> list, int curPageNum) {
		return getCurrentPageList(list, curPageNum, PageUtil.DEFAULT_PER_PAGE_RECORD_NUM);
	}
	
	/**
	 * ����һ���µ�����
	 * @param c
	 * @param length
	 * @return
	 * Object 
	 * author luowei
	 */
	private static Object createNewArray(Class<Object> c, int length) {
		Class<?> componentType = c.getComponentType();
		return Array.newInstance(componentType, length);
	}
	
	/**
	 * ��ȡÿҳ�ĵ�һ��ֵ������
	 * @param totalPageNum
	 * @param curPageNum
	 * @return
	 * int 
	 * author luowei
	 */
	private static int getBeginIndex(int totalPageNum, int curPageNum, int perPageRecordNum) {
		//��ǰҳ��
		int curPage = PageUtil.getCurPage(totalPageNum, curPageNum);
		
		return perPageRecordNum * (curPage - 1);
	}
	
	/**
	 * ��ȡÿҳ���һ��ֵ������
	 * @param perPageRecordNum ÿҳ��¼��
	 * @param totalRecordNum
	 * @param beginIndex
	 * @return
	 * int 
	 * author luowei
	 */
	private static int getEndIndex(int perPageRecordNum, int totalRecordNum, int beginIndex) {
		int endIndex = beginIndex + perPageRecordNum;
		if (endIndex > totalRecordNum) {
			endIndex = totalRecordNum;
		}
		
		return endIndex;
	}
	
	/**
	 * ��ȡ��ǰҳ����
	 * @author luowei
	 * @param totalPageNum
	 * @param curPageNum
	 * @return
	 * int
	 */
	private static int getCurPage(int totalPageNum, int curPageNum) {
		if (curPageNum < 1) {
			return 1;
		}
		if (curPageNum > totalPageNum) {
			return totalPageNum;
		}
		return curPageNum;
	}
	
	/**
	 * ���Զ����Ƿ�Ϊ��
	 * @param obj
	 * void 
	 * author luowei
	 */
	private static void assertNULL(Object obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
	}
	
	/**
	 * ÿҳ�Ŀ�ʼ�����Ƿ�����Ч�ķ�Χ
	 * @param beginIndex
	 * @param totalRecordNum
	 * @return
	 * boolean 
	 * author luowei
	 */
	private static boolean isValidBoundsOfBeginIndex(int beginIndex, int totalRecordNum) {
		if (beginIndex < 0 || beginIndex > totalRecordNum) {
			return true;
		}
		
		return false;
	}
}
