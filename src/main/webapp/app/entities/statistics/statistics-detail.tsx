import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './statistics.reducer';

export const StatisticsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const statisticsEntity = useAppSelector(state => state.statistics.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="statisticsDetailsHeading">
          <Translate contentKey="karadimastyresApp.statistics.detail.title">Statistics</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.id}</dd>
          <dt>
            <span id="todaysales">
              <Translate contentKey="karadimastyresApp.statistics.todaysales">Todaysales</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.todaysales}</dd>
          <dt>
            <span id="totalCustomersNumb">
              <Translate contentKey="karadimastyresApp.statistics.totalCustomersNumb">Total Customers Numb</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.totalCustomersNumb}</dd>
          <dt>
            <span id="custmerTotal">
              <Translate contentKey="karadimastyresApp.statistics.custmerTotal">Custmer Total</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.custmerTotal}</dd>
          <dt>
            <span id="schedTotalNexWeek">
              <Translate contentKey="karadimastyresApp.statistics.schedTotalNexWeek">Sched Total Nex Week</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.schedTotalNexWeek}</dd>
          <dt>
            <span id="totalCarts">
              <Translate contentKey="karadimastyresApp.statistics.totalCarts">Total Carts</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.totalCarts}</dd>
          <dt>
            <span id="totalPending">
              <Translate contentKey="karadimastyresApp.statistics.totalPending">Total Pending</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.totalPending}</dd>
          <dt>
            <span id="totalPayments">
              <Translate contentKey="karadimastyresApp.statistics.totalPayments">Total Payments</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.totalPayments}</dd>
          <dt>
            <span id="pendingPayments">
              <Translate contentKey="karadimastyresApp.statistics.pendingPayments">Pending Payments</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.pendingPayments}</dd>
          <dt>
            <span id="pendingNumberPayments">
              <Translate contentKey="karadimastyresApp.statistics.pendingNumberPayments">Pending Number Payments</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.pendingNumberPayments}</dd>
          <dt>
            <span id="totalCars">
              <Translate contentKey="karadimastyresApp.statistics.totalCars">Total Cars</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.totalCars}</dd>
          <dt>
            <span id="totalTrucks">
              <Translate contentKey="karadimastyresApp.statistics.totalTrucks">Total Trucks</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.totalTrucks}</dd>
          <dt>
            <span id="totalOther1">
              <Translate contentKey="karadimastyresApp.statistics.totalOther1">Total Other 1</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.totalOther1}</dd>
          <dt>
            <span id="totalOther2">
              <Translate contentKey="karadimastyresApp.statistics.totalOther2">Total Other 2</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.totalOther2}</dd>
          <dt>
            <span id="latestpayments">
              <Translate contentKey="karadimastyresApp.statistics.latestpayments">Latestpayments</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.latestpayments}</dd>
          <dt>
            <span id="ltstsupplierpaym">
              <Translate contentKey="karadimastyresApp.statistics.ltstsupplierpaym">Ltstsupplierpaym</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.ltstsupplierpaym}</dd>
          <dt>
            <span id="recentrlycompltd">
              <Translate contentKey="karadimastyresApp.statistics.recentrlycompltd">Recentrlycompltd</Translate>
            </span>
          </dt>
          <dd>{statisticsEntity.recentrlycompltd}</dd>
        </dl>
        <Button tag={Link} to="/statistics" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/statistics/${statisticsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StatisticsDetail;
