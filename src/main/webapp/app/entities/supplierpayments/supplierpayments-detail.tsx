import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './supplierpayments.reducer';

export const SupplierpaymentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const supplierpaymentsEntity = useAppSelector(state => state.supplierpayments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="supplierpaymentsDetailsHeading">
          <Translate contentKey="karadimastyresApp.supplierpayments.detail.title">Supplierpayments</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{supplierpaymentsEntity.id}</dd>
          <dt>
            <span id="invoiceDate">
              <Translate contentKey="karadimastyresApp.supplierpayments.invoiceDate">Invoice Date</Translate>
            </span>
          </dt>
          <dd>
            {supplierpaymentsEntity.invoiceDate ? (
              <TextFormat value={supplierpaymentsEntity.invoiceDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dueDate">
              <Translate contentKey="karadimastyresApp.supplierpayments.dueDate">Due Date</Translate>
            </span>
          </dt>
          <dd>
            {supplierpaymentsEntity.dueDate ? (
              <TextFormat value={supplierpaymentsEntity.dueDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="ispaid">
              <Translate contentKey="karadimastyresApp.supplierpayments.ispaid">Ispaid</Translate>
            </span>
          </dt>
          <dd>{supplierpaymentsEntity.ispaid}</dd>
          <dt>
            <span id="amountDue">
              <Translate contentKey="karadimastyresApp.supplierpayments.amountDue">Amount Due</Translate>
            </span>
          </dt>
          <dd>{supplierpaymentsEntity.amountDue}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="karadimastyresApp.supplierpayments.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{supplierpaymentsEntity.notes}</dd>
          <dt>
            <span id="attachments">
              <Translate contentKey="karadimastyresApp.supplierpayments.attachments">Attachments</Translate>
            </span>
          </dt>
          <dd>
            {supplierpaymentsEntity.attachments ? (
              <div>
                {supplierpaymentsEntity.attachmentsContentType ? (
                  <a onClick={openFile(supplierpaymentsEntity.attachmentsContentType, supplierpaymentsEntity.attachments)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {supplierpaymentsEntity.attachmentsContentType}, {byteSize(supplierpaymentsEntity.attachments)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="tax">
              <Translate contentKey="karadimastyresApp.supplierpayments.tax">Tax</Translate>
            </span>
          </dt>
          <dd>{supplierpaymentsEntity.tax}</dd>
          <dt>
            <span id="shipping">
              <Translate contentKey="karadimastyresApp.supplierpayments.shipping">Shipping</Translate>
            </span>
          </dt>
          <dd>{supplierpaymentsEntity.shipping}</dd>
          <dt>
            <span id="lastupdated">
              <Translate contentKey="karadimastyresApp.supplierpayments.lastupdated">Lastupdated</Translate>
            </span>
          </dt>
          <dd>
            {supplierpaymentsEntity.lastupdated ? (
              <TextFormat value={supplierpaymentsEntity.lastupdated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="karadimastyresApp.supplierpayments.supplier">Supplier</Translate>
          </dt>
          <dd>{supplierpaymentsEntity.supplier ? supplierpaymentsEntity.supplier.company : ''}</dd>
        </dl>
        <Button tag={Link} to="/supplierpayments" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supplierpayments/${supplierpaymentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SupplierpaymentsDetail;
