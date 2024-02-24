import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IStatistics } from 'app/shared/model/statistics.model';
import { getEntities } from './statistics.reducer';

export const Statistics = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const statisticsList = useAppSelector(state => state.statistics.entities);
  const loading = useAppSelector(state => state.statistics.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="statistics-heading" data-cy="StatisticsHeading">
        <Translate contentKey="karadimastyresApp.statistics.home.title">Statistics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="karadimastyresApp.statistics.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/statistics/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="karadimastyresApp.statistics.home.createLabel">Create new Statistics</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {statisticsList && statisticsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.todaysales">Todaysales</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.totalCustomersNumb">Total Customers Numb</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.custmerTotal">Custmer Total</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.schedTotalNexWeek">Sched Total Nex Week</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.totalCarts">Total Carts</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.totalPending">Total Pending</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.totalPayments">Total Payments</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.pendingPayments">Pending Payments</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.pendingNumberPayments">Pending Number Payments</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.totalCars">Total Cars</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.totalTrucks">Total Trucks</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.totalOther1">Total Other 1</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.totalOther2">Total Other 2</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.latestpayments">Latestpayments</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.ltstsupplierpaym">Ltstsupplierpaym</Translate>
                </th>
                <th>
                  <Translate contentKey="karadimastyresApp.statistics.recentrlycompltd">Recentrlycompltd</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {statisticsList.map((statistics, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/statistics/${statistics.id}`} color="link" size="sm">
                      {statistics.id}
                    </Button>
                  </td>
                  <td>{statistics.todaysales}</td>
                  <td>{statistics.totalCustomersNumb}</td>
                  <td>{statistics.custmerTotal}</td>
                  <td>{statistics.schedTotalNexWeek}</td>
                  <td>{statistics.totalCarts}</td>
                  <td>{statistics.totalPending}</td>
                  <td>{statistics.totalPayments}</td>
                  <td>{statistics.pendingPayments}</td>
                  <td>{statistics.pendingNumberPayments}</td>
                  <td>{statistics.totalCars}</td>
                  <td>{statistics.totalTrucks}</td>
                  <td>{statistics.totalOther1}</td>
                  <td>{statistics.totalOther2}</td>
                  <td>{statistics.latestpayments}</td>
                  <td>{statistics.ltstsupplierpaym}</td>
                  <td>{statistics.recentrlycompltd}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/statistics/${statistics.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/statistics/${statistics.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/statistics/${statistics.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="karadimastyresApp.statistics.home.notFound">No Statistics found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Statistics;
