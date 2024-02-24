import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job.reducer';

export const JobDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobEntity = useAppSelector(state => state.job.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobDetailsHeading">
          <Translate contentKey="karadimastyresApp.job.detail.title">Job</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{jobEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="karadimastyresApp.job.name">Name</Translate>
            </span>
          </dt>
          <dd>{jobEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="karadimastyresApp.job.description">Description</Translate>
            </span>
          </dt>
          <dd>{jobEntity.description}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="karadimastyresApp.job.note">Note</Translate>
            </span>
          </dt>
          <dd>{jobEntity.note}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="karadimastyresApp.job.price">Price</Translate>
            </span>
          </dt>
          <dd>{jobEntity.price}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="karadimastyresApp.job.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{jobEntity.priority}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="karadimastyresApp.job.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {jobEntity.image ? (
              <div>
                {jobEntity.imageContentType ? (
                  <a onClick={openFile(jobEntity.imageContentType, jobEntity.image)}>
                    <img src={`data:${jobEntity.imageContentType};base64,${jobEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {jobEntity.imageContentType}, {byteSize(jobEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="karadimastyresApp.job.jobCategory">Job Category</Translate>
          </dt>
          <dd>{jobEntity.jobCategory ? jobEntity.jobCategory.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/job" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job/${jobEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobDetail;
