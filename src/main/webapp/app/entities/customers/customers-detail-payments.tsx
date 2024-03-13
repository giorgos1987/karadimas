import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customers.reducer';
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';

export const CustomersDtPayments = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customersEntity = useAppSelector(state => state.customers.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customersDetailsPaymentHeading">
          <Translate contentKey="karadimastyresApp.customers.detail.title">Customers</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="karadimastyresApp.customers.name">Name</Translate>
            </span>
          </dt>
          <dd>{customersEntity.name}</dd>
          <dt>
            <span id="customerpayments">
              <Translate contentKey="karadimastyresApp.customers.customerpayments">Customerpayments</Translate>
            </span>
          </dt>
          <dd>
            <table className="table table-striped table-hover ">
              <thead>
                <tr>
                  <th>Συνολικό Ποσό</th>
                  <th>Υπόλοιπο</th>
                  <th>Προκαταβολή</th>
                  <th>Ημερομηνία Πληρωμής</th>
                </tr>
              </thead>
              <tbody>
                {customersEntity.customerpayments
                  ? customersEntity.customerpayments.map((customerpayments, i) => (
                      <tr key={customerpayments.id}>
                        <td>{customerpayments.totalAmount}</td>
                        <td>{customerpayments.remainder}</td>
                        <td>{customerpayments.downPayment}</td>
                        <td>{convertDateTimeFromServer(customerpayments.invoiceDate)}</td>
                        {/*{customersEntity.customerpayments && i === customersEntity.customerpayments.length - 1 ? 'Δεν υπάρχουν πληρωμές' : ', '}*/}
                      </tr>
                    ))
                  : null}
              </tbody>
            </table>
          </dd>
        </dl>
        <Button tag={Link} to="/customers" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customers/${customersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomersDtPayments;
