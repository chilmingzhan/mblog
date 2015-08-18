/**
 *
 */
package mblog.persist.service.impl;

import mblog.data.Feeds;
import mblog.data.Post;
import mblog.persist.dao.FeedsDao;
import mblog.persist.entity.FeedsPO;
import mblog.persist.service.FeedsService;
import mblog.persist.service.PostService;
import mblog.persist.utils.BeanMapUtils;
import mtons.modules.pojos.Paging;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

/**
 * @author langhsu
 *
 */
public class FeedsServiceImpl implements FeedsService {
	@Autowired
	private FeedsDao feedsDao;
	@Autowired
	private PostService postService;

	@Override
	@Transactional
	public int add(Feeds feeds) {
		FeedsPO po = new FeedsPO();
		BeanUtils.copyProperties(feeds, po);

		po.setCreated(new Date());

		// 给自己保存一条
		feedsDao.save(po);

		// 派发给粉丝
		int ret = feedsDao.batchAdd(feeds);
		return ret + 1;
	}

	@Override
	@Transactional
	public int deleteByAuthorId(long ownId, long authorId) {
		return feedsDao.deleteByAuthorId(ownId, authorId);
	}

	@Override
	@Transactional(readOnly = true)
	public void findUserFeeds(Paging paging, long ownId, long authorId, long ignoreId) {
		List<FeedsPO> list = feedsDao.findUserFeeds(paging, ownId, authorId, ignoreId);

		List<Feeds> rets = new ArrayList<>();

		Set<Long> postIds = new HashSet<>();

		for (FeedsPO po : list) {
			Feeds f = BeanMapUtils.copy(po);
			rets.add(f);

			postIds.add(f.getPostId());
		}

		// 加载文章
		Map<Long, Post> postMap = postService.findMultileMapByIds(postIds);

		for (Feeds f : rets) {
			f.setPost(postMap.get(f.getPostId()));
		}
		paging.setResults(rets);
	}

	@Override
	@Transactional
	public void deleteByTarget(long postId) {
		feedsDao.deleteByTarget(postId);
	}

}
