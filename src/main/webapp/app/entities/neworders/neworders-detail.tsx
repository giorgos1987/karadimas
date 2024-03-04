import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './neworders.reducer';

export const NewordersDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const newordersEntity = useAppSelector(state => state.neworders.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="newordersDetailsHeading">
          <Translate contentKey="karadimastyresApp.neworders.detail.title">Neworders</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.id}</dd>
          <dt>
            <span id="orderDate">
              <Translate contentKey="karadimastyresApp.neworders.orderDate">Order Date</Translate>
            </span>
          </dt>
          <dd>
            {newordersEntity.orderDate ? <TextFormat value={newordersEntity.orderDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="elastika1">
              <Translate contentKey="karadimastyresApp.neworders.elastika1">Elastika 1</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika1}</dd>
          <dt>
            <span id="elastika2">
              <Translate contentKey="karadimastyresApp.neworders.elastika2">Elastika 2</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika2}</dd>
          <dt>
            <span id="elastika3">
              <Translate contentKey="karadimastyresApp.neworders.elastika3">Elastika 3</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika3}</dd>
          <dt>
            <span id="elastika4">
              <Translate contentKey="karadimastyresApp.neworders.elastika4">Elastika 4</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika4}</dd>
          <dt>
            <span id="elastika5">
              <Translate contentKey="karadimastyresApp.neworders.elastika5">Elastika 5</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika5}</dd>
          <dt>
            <span id="elastika6">
              <Translate contentKey="karadimastyresApp.neworders.elastika6">Elastika 6</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika6}</dd>
          <dt>
            <span id="elastika7">
              <Translate contentKey="karadimastyresApp.neworders.elastika7">Elastika 7</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika7}</dd>
          <dt>
            <span id="elastika8">
              <Translate contentKey="karadimastyresApp.neworders.elastika8">Elastika 8</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika8}</dd>
          <dt>
            <span id="elastika9">
              <Translate contentKey="karadimastyresApp.neworders.elastika9">Elastika 9</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika9}</dd>
          <dt>
            <span id="elastika10">
              <Translate contentKey="karadimastyresApp.neworders.elastika10">Elastika 10</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika10}</dd>
          <dt>
            <span id="elastika11">
              <Translate contentKey="karadimastyresApp.neworders.elastika11">Elastika 11</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika11}</dd>
          <dt>
            <span id="elastika12">
              <Translate contentKey="karadimastyresApp.neworders.elastika12">Elastika 12</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika12}</dd>
          <dt>
            <span id="elastika13">
              <Translate contentKey="karadimastyresApp.neworders.elastika13">Elastika 13</Translate>
            </span>
          </dt>
          <dd>{newordersEntity.elastika13}</dd>
        </dl>
        <Button tag={Link} to="/neworders" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/neworders/${newordersEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NewordersDetail;
