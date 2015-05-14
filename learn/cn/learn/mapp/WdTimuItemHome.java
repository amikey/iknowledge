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
 * Home object for domain model class WdTimuItem.
 * @see cn.learn.mapp.WdTimuItem
 * @author Hibernate Tools
 */
public class WdTimuItemHome {

	private static final Log log = LogFactory.getLog(WdTimuItemHome.class);

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

	public void persist(WdTimuItem transientInstance) {
		log.debug("persisting WdTimuItem instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(WdTimuItem instance) {
		log.debug("attaching dirty WdTimuItem instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(WdTimuItem instance) {
		log.debug("attaching clean WdTimuItem instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(WdTimuItem persistentInstance) {
		log.debug("deleting WdTimuItem instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WdTimuItem merge(WdTimuItem detachedInstance) {
		log.debug("merging WdTimuItem instance");
		try {
			WdTimuItem result = (WdTimuItem) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WdTimuItem findById(java.lang.Integer id) {
		log.debug("getting WdTimuItem instance with id: " + id);
		try {
			WdTimuItem instance = (WdTimuItem) sessionFactory
					.getCurrentSession().get("cn.learn.mapp.WdTimuItem", id);
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

	public List findByExample(WdTimuItem instance) {
		log.debug("finding WdTimuItem instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.learn.mapp.WdTimuItem")
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
