import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-category.reducer';

export const JobCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobCategoryEntity = useAppSelector(state => state.jobCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobCategoryDetailsHeading">
          <Translate contentKey="karadimastyresApp.jobCategory.detail.title">JobCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobCategoryEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="karadimastyresApp.jobCategory.name">Name</Translate>
            </span>
          </dt>
          <dd>{jobCategoryEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="karadimastyresApp.jobCategory.description">Description</Translate>
            </span>
          </dt>
          <dd>{jobCategoryEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/job-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-category/${jobCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobCategoryDetail;
