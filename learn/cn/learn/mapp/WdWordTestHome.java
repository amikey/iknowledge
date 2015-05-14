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
 * Home object for domain model class WdWordTest.
 * @see cn.learn.mapp.WdWordTest
 * @author Hibernate Tools
 */
public class WdWordTestHome {

	private static final Log log = LogFactory.getLog(WdWordTestHome.class);

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

	public void persist(WdWordTest transientInstance) {
		log.debug("persisting WdWordTest instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(WdWordTest instance) {
		log.debug("attaching dirty WdWordTest instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(WdWordTest instance) {
		log.debug("attaching clean WdWordTest instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(WdWordTest persistentInstance) {
		log.debug("deleting WdWordTest instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WdWordTest merge(WdWordTest detachedInstance) {
		log.debug("merging WdWordTest instance");
		try {
			WdWordTest result = (WdWordTest) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WdWordTest findById(java.lang.Integer id) {
		log.debug("getting WdWordTest instance with id: " + id);
		try {
			WdWordTest instance = (WdWordTest) sessionFactory
					.getCurrentSession().get("cn.learn.mapp.WdWordTest", id);
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

	public List findByExample(WdWordTest instance) {
		log.debug("finding WdWordTest instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.learn.mapp.WdWordTest")
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
