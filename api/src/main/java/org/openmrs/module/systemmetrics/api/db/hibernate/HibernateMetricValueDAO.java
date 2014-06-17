package org.openmrs.module.systemmetrics.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.systemmetrics.MetricValue;
import org.openmrs.module.systemmetrics.api.PerMinMetricValue;
import org.openmrs.module.systemmetrics.api.db.MetricValueDAO;

import java.util.List;

public class HibernateMetricValueDAO implements MetricValueDAO {

    private static Log log = LogFactory.getLog(HibernateMetricValueDAO.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int getMetricId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getMetricValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getMetricTimestamp() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public MetricValue addMetricValue(MetricValue metricValue) {
        sessionFactory.getCurrentSession().saveOrUpdate(metricValue);
        return metricValue;
    }

    @Override
    public void removeMetricValue(MetricValue metricValue) {
        sessionFactory.getCurrentSession().delete(metricValue);
    }

    @Override
    public PerMinMetricValue getMetricValue(long startTimestamp, long endTimestamp) {
       Query query =  sessionFactory.getCurrentSession().createQuery("from MetricValue where timestamp > :startTimestamp and timestamp < :endTimestamp").setParameter("startTimestamp", startTimestamp).setParameter("endTimestamp", endTimestamp);
       List<MetricValue> valueList = query.list();
       long averageValue = 0;
       long sumOfVals = 0;
        for(MetricValue metricVal :valueList )  {
           sumOfVals += metricVal.getMetricValue();
        }
       averageValue = sumOfVals/valueList.size();
       return new PerMinMetricValue(1,System.currentTimeMillis(), averageValue);
    }


}
