/**
 * 
 */
package mblog.persist.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mtons.modules.pojos.Paging;

import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import mblog.data.Post;

/**
 * 文章管理
 * @author langhsu
 *
 */
public interface PostService {
	/**
	 * 分页查询所有文章
	 * 
	 * @param paging
	 * @param group 分组Id
	 * @param ord
	 * @param whetherHasAlbums 是否加载图片
	 */
	void paging(Paging paging, int group, String ord, boolean whetherHasAlbums);
	
	/**
	 * 查询个人发布文章
	 * @param paging
	 * @param userId
	 */
	void pagingByUserId(Paging paging, long userId);
	
	/**
	 * 根据关键字搜索
	 * @param paging
	 * @param q
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	void search(Paging paging, String q) throws InterruptedException, IOException, InvalidTokenOffsetsException;
	
	/**
	 * 搜索 Tag
	 * @param paging
	 * @param tag
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException
	 */
	void searchByTag(Paging paging, String tag) throws InterruptedException, IOException, InvalidTokenOffsetsException;
	
	/**
	 * 查询最近更新 - 按发布时间排序
	 * @param maxResutls
	 * @param ignoreUserId
	 * @return
	 */
	List<Post> findLatests(int maxResutls, long ignoreUserId);

	/**
	 * 查询热门文章 - 按浏览次数排序
	 * @param maxResutls
	 * @param ignoreUserId
	 * @return
	 */
	List<Post> findHots(int maxResutls, long ignoreUserId);
	
	/**
	 * 根据Ids查询 - 单图
	 * @param ids
	 * @return <id, 文章对象>
	 */
	Map<Long, Post> findSingleMapByIds(Set<Long> ids);

	/**
	 * 根据Ids查询 - 多图
	 * @param ids
	 * @return <id, 文章对象>
	 */
	Map<Long, Post> findMultileMapByIds(Set<Long> ids);
	
	/**
	 * 发布文章
	 * @param post
	 */
	long post(Post post);
	
	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	Post get(long id);

	/**
	 * 更新文章方法
	 * @param p
	 */
	void update(Post p);
	
	/**
	 * 删除
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * 带作者验证的删除 - 验证是否属于自己的文章
	 * @param id
	 * @param authorId
	 */
	void delete(long id, long authorId);
	
	/**
	 * 自增浏览数
	 * @param id
	 */
	void identityViews(long id);
	
	/**
	 * 自增喜欢数
	 * @param id
	 */
	void identityFavors(long id);
	
	/**
	 * 自增评论数
	 * @param id
	 */
	void identityComments(long id);

}
