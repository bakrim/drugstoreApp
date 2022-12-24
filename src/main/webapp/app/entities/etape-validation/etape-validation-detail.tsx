import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './etape-validation.reducer';

export const EtapeValidationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const etapeValidationEntity = useAppSelector(state => state.etapeValidation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="etapeValidationDetailsHeading">
          <Translate contentKey="drugStoreApp.etapeValidation.detail.title">EtapeValidation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{etapeValidationEntity.id}</dd>
          <dt>
            <span id="libelle">
              <Translate contentKey="drugStoreApp.etapeValidation.libelle">Libelle</Translate>
            </span>
          </dt>
          <dd>{etapeValidationEntity.libelle}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="drugStoreApp.etapeValidation.description">Description</Translate>
            </span>
          </dt>
          <dd>{etapeValidationEntity.description}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="drugStoreApp.etapeValidation.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {etapeValidationEntity.date ? <TextFormat value={etapeValidationEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="decision">
              <Translate contentKey="drugStoreApp.etapeValidation.decision">Decision</Translate>
            </span>
          </dt>
          <dd>{etapeValidationEntity.decision}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.etapeValidation.dossierPharmacie">Dossier Pharmacie</Translate>
          </dt>
          <dd>{etapeValidationEntity.dossierPharmacie ? etapeValidationEntity.dossierPharmacie.id : ''}</dd>
          <dt>
            <Translate contentKey="drugStoreApp.etapeValidation.dossierAutre">Dossier Autre</Translate>
          </dt>
          <dd>{etapeValidationEntity.dossierAutre ? etapeValidationEntity.dossierAutre.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/etape-validation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/etape-validation/${etapeValidationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EtapeValidationDetail;
