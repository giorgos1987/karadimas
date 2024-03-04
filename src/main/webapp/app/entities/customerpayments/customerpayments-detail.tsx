import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customerpayments.reducer';

export const CustomerpaymentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customerpaymentsEntity = useAppSelector(state => state.customerpayments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerpaymentsDetailsHeading">
          <Translate contentKey="karadimastyresApp.customerpayments.detail.title">Customerpayments</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerpaymentsEntity.id}</dd>
          <dt>
            <span id="totalAmount">
              <Translate contentKey="karadimastyresApp.customerpayments.totalAmount">Total Amount</Translate>
            </span>
          </dt>
          <dd>{customerpaymentsEntity.totalAmount}</dd>
          <dt>
            <span id="remainder">
              <Translate contentKey="karadimastyresApp.customerpayments.remainder">Remainder</Translate>
            </span>
          </dt>
          <dd>{customerpaymentsEntity.remainder}</dd>
          <dt>
            <span id="downPayment">
              <Translate contentKey="karadimastyresApp.customerpayments.downPayment">Down Payment</Translate>
            </span>
          </dt>
          <dd>{customerpaymentsEntity.downPayment}</dd>
          <dt>
            <span id="ispaid">
              <Translate contentKey="karadimastyresApp.customerpayments.ispaid">Ispaid</Translate>
            </span>
          </dt>
          <dd>{customerpaymentsEntity.ispaid}</dd>
          <dt>
            <span id="invoiceDate">
              <Translate contentKey="karadimastyresApp.customerpayments.invoiceDate">Invoice Date</Translate>
            </span>
          </dt>
          <dd>
            {customerpaymentsEntity.invoiceDate ? (
              <TextFormat value={customerpaymentsEntity.invoiceDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dueDate">
              <Translate contentKey="karadimastyresApp.customerpayments.dueDate">Due Date</Translate>
            </span>
          </dt>
          <dd>
            {customerpaymentsEntity.dueDate ? (
              <TextFormat value={customerpaymentsEntity.dueDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="notes">
              <Translate contentKey="karadimastyresApp.customerpayments.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{customerpaymentsEntity.notes}</dd>
          <dt>
            <span id="attachments">
              <Translate contentKey="karadimastyresApp.customerpayments.attachments">Attachments</Translate>
            </span>
          </dt>
          <dd>
            {customerpaymentsEntity.attachments ? (
              <div>
                {customerpaymentsEntity.attachmentsContentType ? (
                  <a onClick={openFile(customerpaymentsEntity.attachmentsContentType, customerpaymentsEntity.attachments)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {customerpaymentsEntity.attachmentsContentType}, {byteSize(customerpaymentsEntity.attachments)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="lastupdated">
              <Translate contentKey="karadimastyresApp.customerpayments.lastupdated">Lastupdated</Translate>
            </span>
          </dt>
          <dd>
            {customerpaymentsEntity.lastupdated ? (
              <TextFormat value={customerpaymentsEntity.lastupdated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="karadimastyresApp.customerpayments.customers">Customers</Translate>
          </dt>
          <dd>{customerpaymentsEntity.customers ? customerpaymentsEntity.customers.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/customerpayments" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customerpayments/${customerpaymentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerpaymentsDetail;
