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
 * Home object for domain model class WdTimu.
 * @see cn.learn.mapp.WdTimu
 * @author Hibernate Tools
 */
public class WdTimuHome {

	private static final Log log = LogFactory.getLog(WdTimuHome.class);

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

	public void persist(WdTimu transientInstance) {
		log.debug("persisting WdTimu instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(WdTimu instance) {
		log.debug("attaching dirty WdTimu instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(WdTimu instance) {
		log.debug("attaching clean WdTimu instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(WdTimu persistentInstance) {
		log.debug("deleting WdTimu instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public WdTimu merge(WdTimu detachedInstance) {
		log.debug("merging WdTimu instance");
		try {
			WdTimu result = (WdTimu) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public WdTimu findById(java.lang.Integer id) {
		log.debug("getting WdTimu instance with id: " + id);
		try {
			WdTimu instance = (WdTimu) sessionFactory.getCurrentSession().get(
					"cn.learn.mapp.WdTimu", id);
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

	public List findByExample(WdTimu instance) {
		log.debug("finding WdTimu instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("cn.learn.mapp.WdTimu")
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
