import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICommission } from 'app/shared/model/commission.model';
import { getEntities } from './commission.reducer';

export const Commission = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const commissionList = useAppSelector(state => state.commission.entities);
  const loading = useAppSelector(state => state.commission.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="commission-heading" data-cy="CommissionHeading">
        <Translate contentKey="drugStoreApp.commission.home.title">Commissions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="drugStoreApp.commission.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/commission/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="drugStoreApp.commission.home.createLabel">Create new Commission</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {commissionList && commissionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="drugStoreApp.commission.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.commission.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.commission.decision">Decision</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.commission.motif">Motif</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.commission.representantList">Representant List</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.commission.dossierPharmacie">Dossier Pharmacie</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {commissionList.map((commission, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/commission/${commission.id}`} color="link" size="sm">
                      {commission.id}
                    </Button>
                  </td>
                  <td>{commission.date ? <TextFormat type="date" value={commission.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{commission.decision}</td>
                  <td>{commission.motif}</td>
                  <td>
                    {commission.representantLists
                      ? commission.representantLists.map((val, j) => (
                          <span key={j}>
                            <Link to={`/representant/${val.id}`}>{val.id}</Link>
                            {j === commission.representantLists.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {commission.dossierPharmacie ? (
                      <Link to={`/dossier-pharmacie/${commission.dossierPharmacie.id}`}>{commission.dossierPharmacie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/commission/${commission.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/commission/${commission.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/commission/${commission.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="drugStoreApp.commission.home.notFound">No Commissions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Commission;
