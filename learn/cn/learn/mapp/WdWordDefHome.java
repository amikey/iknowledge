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
 * Home object for domain model class WdWordDef.
 * @see cn.learn.mapp.WdWordDef
 * @author Hibernate Tools
 */
public class WdWordDefHome {

	private static final Log log = LogFactory.getLog(WdWordDefHome.class);

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

	public void persist(WdWordDef transientInstance) {
		log.debug("persisting WdWordDef instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(WdWordDef instance) {
		log.debug("attaching dirty WdWordDef instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(WdWordDef instance) {
		log.debug("attaching clean WdWordDef instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(WdWordDef persistentInstance) {
		log.debug("deleting WdWordDef instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WdWordDef merge(WdWordDef detachedInstance) {
		log.debug("merging WdWordDef instance");
		try {
			WdWordDef result = (WdWordDef) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WdWordDef findById(java.lang.Integer id) {
		log.debug("getting WdWordDef instance with id: " + id);
		try {
			WdWordDef instance = (WdWordDef) sessionFactory.getCurrentSession()
					.get("cn.learn.mapp.WdWordDef", id);
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

	public List findByExample(WdWordDef instance) {
		log.debug("finding WdWordDef instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.learn.mapp.WdWordDef")
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
