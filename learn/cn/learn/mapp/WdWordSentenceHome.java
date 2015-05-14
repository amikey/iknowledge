package cn.learn.mapp;

// Generated 2015-5-14 10:00:42 by Hibernate Tools 3.4.0.CR1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class WdWordSentence.
 * @see cn.learn.mapp.WdWordSentence
 * @author Hibernate Tools
 */
public class WdWordSentenceHome {

	private static final Log log = LogFactory.getLog(WdWordSentenceHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(WdWordSentence transientInstance) {
		log.debug("persisting WdWordSentence instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(WdWordSentence instance) {
		log.debug("attaching dirty WdWordSentence instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(WdWordSentence instance) {
		log.debug("attaching clean WdWordSentence instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(WdWordSentence persistentInstance) {
		log.debug("deleting WdWordSentence instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WdWordSentence merge(WdWordSentence detachedInstance) {
		log.debug("merging WdWordSentence instance");
		try {
			WdWordSentence result = (WdWordSentence) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WdWordSentence findById(java.lang.Integer id) {
		log.debug("getting WdWordSentence instance with id: " + id);
		try {
			WdWordSentence instance = (WdWordSentence) sessionFactory
					.getCurrentSession()
					.get("cn.learn.mapp.WdWordSentence", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(WdWordSentence instance) {
		log.debug("finding WdWordSentence instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.learn.mapp.WdWordSentence")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
